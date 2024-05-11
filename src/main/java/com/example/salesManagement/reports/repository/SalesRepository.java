package com.example.salesManagement.reports.repository;

import com.example.salesManagement.product.dto.ProductDto;
import com.example.salesManagement.reports.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    long countByIsSuccessfulTrueAndSalesDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT SUM(s.totalSales) FROM Sales s WHERE s.isSuccessful = true AND s.salesDate BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalSalesByIsSuccessfulTrueAndSalesDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT NEW com.example.salesManagement.product.dto.ProductDto(s.productId, s.productName, s.price, s.quantitySold, s.totalSales) " +
            "FROM Sales s " +
            "WHERE s.salesDate BETWEEN :startDate AND :endDate " +
            "GROUP BY s.productId, s.productName, s.price, s.quantitySold, s.totalSales " +
            "ORDER BY SUM(s.quantitySold) DESC")
    List<ProductDto> findBestSellingProducts(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
