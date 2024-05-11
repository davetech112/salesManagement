package com.example.salesManagement.user.DTOs;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
public class SellerDto {
    private Long sellerId;
    private String firstName;
    private String lastName;
    private String location;
    private Long totalSales;
    private BigDecimal totalSpend;
    private BigDecimal totalRevenue;


    public SellerDto(Long sellerId, String firstName, String lastName, String location, Long totalSales, BigDecimal totalSpend, BigDecimal totalRevenue) {
        this.sellerId = sellerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.totalSales = totalSales;
        this.totalSpend = totalSpend;
        this.totalRevenue = totalRevenue;
    }
}
