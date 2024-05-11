package com.example.salesManagement.reports.dto;

import com.example.salesManagement.reports.enums.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductActionRequest {
    private Long productId;
    private String productName;
    private Long sellerId;
    private String category;
    private String sellerName;
    private Action action;
    private String description;
    private LocalDateTime changeDate;
}
