package com.example.salesManagement.reports.repository;

import com.example.salesManagement.reports.model.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActionRepository extends JpaRepository<UserAction, Long> {
    List<UserAction> findAllByOrderByChangeDateDesc();
}
