package com.example.salesManagement.reports.model;

import com.example.salesManagement.reports.enums.Action;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_update")
public class ProductUpdate {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private String productName;
    private String category;
    private Long sellerId;
    private String sellerName;
    @Enumerated(EnumType.STRING)
    private Action action;
    private String description;
    private LocalDateTime changeDate;
}
