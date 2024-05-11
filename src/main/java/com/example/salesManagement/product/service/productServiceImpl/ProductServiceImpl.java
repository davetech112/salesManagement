package com.example.salesManagement.product.service.productServiceImpl;

import com.example.salesManagement.exception.NotFoundException;
import com.example.salesManagement.exception.ValidationException;
import com.example.salesManagement.product.dto.ProductRequest;
import com.example.salesManagement.product.dto.ProductResponseDto;
import com.example.salesManagement.product.dto.ProductUpdateRequest;
import com.example.salesManagement.product.model.Product;
import com.example.salesManagement.product.productRepository.ProductRepository;
import com.example.salesManagement.product.service.ProductService;
import com.example.salesManagement.reports.dto.ProductActionRequest;
import com.example.salesManagement.reports.enums.Action;
import com.example.salesManagement.reports.service.ProductUpdateService;
import com.example.salesManagement.user.Model.User;
import com.example.salesManagement.user.enums.Role;
import com.example.salesManagement.user.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductUpdateService productUpdateService;

    public String createProduct(ProductRequest request) {
        var sellerId = request.getSellerId();
        if (!isSeller(sellerId)) {
            throw new ValidationException("Unauthorized operation");
        }
        User user = userRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setInitialQuantity(request.getQuantity());
        product.setUser(user);
        productRepository.save(product);
        sendProductReport(product, user, Action.CREATE, "Product created");
        return "Product created successfully";
    }

    public String updateProduct(Long productId, ProductUpdateRequest updateRequest) {
        if (!isSeller(updateRequest.getUserId())) {
            throw new ValidationException("Unauthorized operation");
        }
        User user = userRepository.findById(updateRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        if (!user.getId().equals(updateRequest.getUserId())) {
            throw new ValidationException("unauthorized operation");
        }
        product.setProductName(updateRequest.getProductName());
        product.setCategory(updateRequest.getCategory());
        //increase quantity
        product.setInitialQuantity(product.getInitialQuantity() + updateRequest.getQuantity());
        product.setPrice(updateRequest.getPrice());
        product.setUser(user);
        productRepository.save(product);
        sendProductReport(product, user, Action.UPDATE, "Product updated");
        return "Product updated successfully";
    }

    @Modifying
    @Transactional
    public String deleteProduct(Long productId, Long sellerId) {
        User user = userRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        if (!sellerId.equals(product.getUser().getId())) {
            throw new ValidationException("unauthorized operation");
        }
        productRepository.delete(product);
        sendProductReport(product, user, Action.DELETE, "Product deleted");
        return "Delete operation successful";
    }

    public ProductResponseDto getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        return ProductResponseDto.builder()
                .id(productId)
                .userId(product.getUser().getId())
                .category(product.getCategory())
                .productName(product.getProductName())
                .totalRevenue(product.getTotalRevenue())
                .quantitySold(product.getQuantitySold())
                .initialQuantity(product.getInitialQuantity())
                .price(product.getPrice())
                .build();
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return mapAndReturnProducts(products);
    }

    private List<ProductResponseDto> mapAndReturnProducts(List<Product> products) {
        return products.stream()
                .map(product -> ProductResponseDto.builder()
                        .id(product.getId())
                        .productName(product.getProductName())
                        .userId(product.getUser().getId())
                        .category(product.getCategory())
                        .price(product.getPrice())
                        .initialQuantity(product.getInitialQuantity())
                        .totalRevenue(product.getTotalRevenue())
                        .quantitySold(product.getQuantitySold())
                        .build())
                .collect(Collectors.toList());
    }

    private void sendProductReport(Product product, User user, Action action, String description) {
        ProductActionRequest request = ProductActionRequest.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .sellerId(user.getId())
                .category(product.getCategory())
                .sellerName(description)
                .action(action)
                .description(description)
                .changeDate(LocalDateTime.now())
                .build();
        productUpdateService.updateProductAction(request);
    }

    private boolean isSeller(Long userId) {
        return userRepository.findById(userId)
                .map(user -> user.getRole().equals(Role.SELLER))
                .orElse(false);
    }
}
