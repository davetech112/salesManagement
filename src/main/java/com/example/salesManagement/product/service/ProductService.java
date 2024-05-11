package com.example.salesManagement.product.service;

import com.example.salesManagement.product.dto.ProductRequest;
import com.example.salesManagement.product.dto.ProductResponseDto;
import com.example.salesManagement.product.dto.ProductUpdateRequest;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProduct(Long productId);

    String deleteProduct(Long productId, Long sellerId);

    String updateProduct(Long productId, ProductUpdateRequest updateRequest);

    String createProduct(ProductRequest request);
}
