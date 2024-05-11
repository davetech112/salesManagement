package com.example.salesManagement.user.userRepository;

import com.example.salesManagement.user.DTOs.SellerDto;
import com.example.salesManagement.user.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT NEW com.example.salesManagement.user.DTOs.SellerDto(u.id, u.firstName, u.lastName, u.location, u.totalSales, u.totalSpend, u.totalRevenue) " +
            "FROM User u " +
            "WHERE u.role = 'SELLER' AND u.createdDate BETWEEN :startDate AND :endDate " +
            "ORDER BY u.totalRevenue DESC")
    List<SellerDto> findAllSortedByHighestRevenue(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'CLIENT'")
    long countClients();

    @Query("SELECT u FROM User u WHERE u.role = 'CLIENT' ORDER BY u.totalSpend DESC LIMIT 1")
    Optional<User> findClientWithHighestSpend();

}
