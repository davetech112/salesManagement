package com.example.salesManagement.user.userRepository;

import com.example.salesManagement.user.Model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    boolean existsByWalletAccountId(Long walletAccountId);
}
