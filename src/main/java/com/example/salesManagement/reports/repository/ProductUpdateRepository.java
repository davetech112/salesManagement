package com.example.salesManagement.reports.repository;

import com.example.salesManagement.reports.model.ProductUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductUpdateRepository extends JpaRepository<ProductUpdate, Long> {
    List<ProductUpdate> findAllByOrderByChangeDateDesc();
}
