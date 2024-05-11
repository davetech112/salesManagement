package com.example.salesManagement.reports.dto;

import com.example.salesManagement.product.dto.ProductDto;
import com.example.salesManagement.user.DTOs.SellerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesReportDto {
    private Long totalNumberOfSales;
    private BigDecimal totalRevenue;
    private List<ProductDto> topSellingProducts;
    private List<SellerDto> topPerformingSellers;
}
