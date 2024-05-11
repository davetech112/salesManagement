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
public class ProductResponseDto {
    private Long id;
    private String productName;
    private Long userId;
    private String category;
    private BigDecimal price;
    private Long initialQuantity;
    private Long quantitySold;
    private BigDecimal totalRevenue;
}
