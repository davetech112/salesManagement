package com.example.salesManagement.product.model;

import com.example.salesManagement.user.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_table")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String productName;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String category;
    private BigDecimal price;
    private Long initialQuantity = 0L;
    private Long quantitySold = 0L;
    private BigDecimal totalRevenue = BigDecimal.ZERO;
}
