package com.example.salesManagement.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesDTO {
    private Long clientId;
    private Long sellerId;
    private BigDecimal price;
    private Long productId;
    private Long quantity;
}
