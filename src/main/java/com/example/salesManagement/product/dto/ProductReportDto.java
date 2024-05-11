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
public class ProductReportDto {
    private Long productId;
    private String productName;
    private Long availableQuantity;
    private BigDecimal price;
    private Long totalSales;
    private BigDecimal totalRevenue;
}
