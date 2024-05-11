package com.example.salesManagement.reports.service.serviceImpl;

import com.example.salesManagement.reports.dto.ProductActionRequest;
import com.example.salesManagement.reports.model.ProductUpdate;
import com.example.salesManagement.reports.repository.ProductUpdateRepository;
import com.example.salesManagement.reports.service.ProductUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdateService {
    private final ProductUpdateRepository productUpdateRepository;

    public void updateProductAction(ProductActionRequest request) {
        ProductUpdate productUpdate = new ProductUpdate();
        productUpdate.setProductId(request.getProductId());
        productUpdate.setAction(request.getAction());
        productUpdate.setCategory(request.getCategory());
        productUpdate.setProductName(request.getProductName());
        productUpdate.setDescription(request.getDescription());
        productUpdate.setChangeDate(request.getChangeDate());
        productUpdate.setSellerId(request.getSellerId());
        productUpdate.setSellerName(request.getSellerName());
        productUpdateRepository.save(productUpdate);
    }

    public List<ProductUpdate> productUpdateReport() {
        return productUpdateRepository.findAllByOrderByChangeDateDesc();
    }

}
