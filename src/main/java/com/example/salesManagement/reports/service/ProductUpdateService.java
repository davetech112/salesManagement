package com.example.salesManagement.reports.service;

import com.example.salesManagement.reports.dto.ProductActionRequest;
import com.example.salesManagement.reports.model.ProductUpdate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductUpdateService {
    void updateProductAction(ProductActionRequest request);

    List<ProductUpdate> productUpdateReport();
}
