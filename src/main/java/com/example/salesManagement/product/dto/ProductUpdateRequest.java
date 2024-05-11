package com.example.salesManagement.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateRequest {
    private String productName;
    private String category;
    private Long userId;
    private BigDecimal price;
    private Long quantity;
}
