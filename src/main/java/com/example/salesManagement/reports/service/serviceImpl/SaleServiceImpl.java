package com.example.salesManagement.reports.service.serviceImpl;

import com.example.salesManagement.exception.NotFoundException;
import com.example.salesManagement.product.dto.ProductReportDto;
import com.example.salesManagement.product.model.Product;
import com.example.salesManagement.product.productRepository.ProductRepository;
import com.example.salesManagement.reports.dto.ClientReportDto;
import com.example.salesManagement.reports.dto.SalesDTO;
import com.example.salesManagement.reports.dto.SalesReportDto;
import com.example.salesManagement.reports.model.Sales;
import com.example.salesManagement.reports.repository.SalesRepository;
import com.example.salesManagement.reports.service.SaleService;
import com.example.salesManagement.user.DTOs.ClientDto;
import com.example.salesManagement.user.Model.User;
import com.example.salesManagement.user.Model.Wallet;
import com.example.salesManagement.user.userRepository.UserRepository;
import com.example.salesManagement.user.userRepository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WalletRepository walletRepository;
    private final SalesRepository salesRepository;

    public SalesReportDto salesReport(LocalDateTime startDate, LocalDateTime endDate) {
        SalesReportDto report = new SalesReportDto();
        report.setTotalNumberOfSales(salesRepository.countByIsSuccessfulTrueAndSalesDateBetween(startDate, endDate));
        report.setTotalRevenue(salesRepository.sumTotalSalesByIsSuccessfulTrueAndSalesDateBetween(startDate, endDate));
        report.setTopPerformingSellers(userRepository.findAllSortedByHighestRevenue(startDate, endDate));
        report.setTopSellingProducts(salesRepository.findBestSellingProducts(startDate, endDate));
        return report;
    }

    public ClientReportDto clientReport() {
        User client = userRepository.findClientWithHighestSpend().orElseThrow(() -> new NotFoundException("No user fits this criteria for report"));
        ClientDto clientDto = new ClientDto();
        clientDto.setClientId(client.getId());
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setLocation(client.getLocation());
        clientDto.setLocation(client.getLocation());
        clientDto.setTotalSpend(client.getTotalSpend());

        ClientReportDto report = new ClientReportDto();
        report.setTopSpendingClient(clientDto);
        report.setTotalNumberOfClients(userRepository.countClients());
        return report;
    }

    public ProductReportDto productReport(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("product not found"));
        return ProductReportDto.builder()
                .productId(productId)
                .productName(product.getProductName())
                .price(product.getPrice())
                .availableQuantity(product.getInitialQuantity())
                .totalSales(product.getQuantitySold())
                .totalRevenue(product.getTotalRevenue())
                .build();
    }

    @Transactional
    public String processSales(List<SalesDTO> salesDTOs) {
        salesDTOs.stream()
                .map(this::processSingleSale)
                .collect(Collectors.toList());
        return "Transaction processed successfully";
    }

    private Sales processSingleSale(SalesDTO salesDTO) {
        Long clientId = salesDTO.getClientId();
        Long sellerId = salesDTO.getSellerId();
        BigDecimal price = salesDTO.getPrice();
        Long quantitySold = salesDTO.getQuantity();

        // Retrieve client and seller from repository
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Client not found"));
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller not found"));

        // Debit client's wallet
        Wallet clientWallet = client.getWallet();
        BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantitySold));

        if (clientWallet.getBalance().compareTo(totalPrice) < 0) {
            Product product = new Product();
            // Set isSuccessful to false in case of insufficient balance
            return createSalesRecord(salesDTO, totalPrice, client, seller, false, product);
        }
        clientWallet.setBalance(clientWallet.getBalance().subtract(totalPrice));

        // Update inventory
        Product product = productRepository.findById(salesDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));
        long remainingQuantity = product.getInitialQuantity() - product.getQuantitySold();
        if (remainingQuantity < quantitySold) {
            // Set isSuccessful to false in case of insufficient inventory
            return createSalesRecord(salesDTO, totalPrice, client, seller, false, product);
        }
        product.setQuantitySold(product.getQuantitySold() + quantitySold);
        product.setTotalRevenue(product.getTotalRevenue().add(totalPrice));

        // Update total sales
        seller.setTotalRevenue(seller.getTotalRevenue().add(totalPrice));
        // Update total quantity sold
        seller.setTotalSales(seller.getTotalSales() + quantitySold);
        // Update client total spend
        client.setTotalSpend(client.getTotalSpend().add(totalPrice));

        walletRepository.save(clientWallet);
        productRepository.save(product);
        userRepository.save(seller);
        userRepository.save(client);

        // Create successful sales record
        return createSalesRecord(salesDTO, totalPrice, client, seller, true, product);
    }

    private Sales createSalesRecord(SalesDTO salesDTO, BigDecimal totalPrice,
                                    User client, User seller, boolean isSuccessful, Product product) {
        Sales sale = Sales.builder()
                .clientId(salesDTO.getClientId())
                .clientName(client.getFirstName() + " " + client.getLastName())
                .sellerId(salesDTO.getSellerId())
                .sellerName(seller.getFirstName() + " " + seller.getLastName())
                .isSuccessful(isSuccessful)
                .price(salesDTO.getPrice())
                .quantitySold(salesDTO.getQuantity())
                .totalSales(totalPrice)
                .salesDate(LocalDateTime.now())
                .productId(product.getId())
                .productName(product.getProductName())
                .build();
        salesRepository.save(sale);
        return sale;
    }

}
