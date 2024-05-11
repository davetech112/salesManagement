package com.example.salesManagement.reports.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales_table")
public class Sales {
    @Id
    @GeneratedValue
    private Long id;
    private Long clientId;
    private String clientName;
    private Long productId;
    private String productName;
    private Long sellerId;
    private String sellerName;
    private boolean isSuccessful;
    private BigDecimal price;
    private Long quantitySold;
    private BigDecimal totalSales;
    private LocalDateTime salesDate;
}
