package com.example.salesManagement.user.service.serviceImpl;

import com.example.salesManagement.user.service.WalletService;
import com.example.salesManagement.user.userRepository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;


    @Override
    public Long generateWalletAccount() {
        return ThreadLocalRandom.current().longs(1000000000L, 10000000000L)
                .filter(number -> !walletRepository.existsByWalletAccountId(number))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unable to generate a unique wallet number"));

    }
}
