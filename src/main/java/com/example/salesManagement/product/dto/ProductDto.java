package com.example.salesManagement.product.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class ProductDto {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Long quantitySold;
    private BigDecimal totalSales;

    public ProductDto(Long productId, String productName, BigDecimal price, Long quantitySold, BigDecimal totalSales) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantitySold = quantitySold;
        this.totalSales = totalSales;
    }

}
