package com.example.salesManagement.reports.service;

import com.example.salesManagement.product.dto.ProductReportDto;
import com.example.salesManagement.reports.dto.ClientReportDto;
import com.example.salesManagement.reports.dto.SalesDTO;
import com.example.salesManagement.reports.dto.SalesReportDto;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {
    String processSales(List<SalesDTO> salesDTOs);

    SalesReportDto salesReport(LocalDateTime startDate, LocalDateTime endDate);

    ClientReportDto clientReport();

    ProductReportDto productReport(Long productId);
}
