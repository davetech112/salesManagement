package com.example.salesManagement.user.service.serviceImpl;

import com.example.salesManagement.config.JwtService;
import com.example.salesManagement.exception.NotFoundException;
import com.example.salesManagement.exception.ValidationException;
import com.example.salesManagement.reports.dto.UserActionRequest;
import com.example.salesManagement.reports.enums.Action;
import com.example.salesManagement.reports.service.UserActionService;
import com.example.salesManagement.user.DTOs.AuthenticationRequest;
import com.example.salesManagement.user.DTOs.AuthenticationResponse;
import com.example.salesManagement.user.DTOs.RegisterRequest;
import com.example.salesManagement.user.Model.User;
import com.example.salesManagement.user.Model.Wallet;
import com.example.salesManagement.user.enums.Role;
import com.example.salesManagement.user.service.AuthenticationService;
import com.example.salesManagement.user.service.WalletService;
import com.example.salesManagement.user.userRepository.UserRepository;
import com.example.salesManagement.user.userRepository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final WalletService walletService;
    private final UserActionService userActionService;
    private final WalletRepository walletRepository;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        String role = request.getRole();
        if (!isValidRole(role)) {
            throw new ValidationException("Invalid role type");
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(role.toUpperCase()))
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .location(request.getLocation())
                .createdDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setWalletAccountId(walletService.generateWalletAccount());
        wallet.setBalance(BigDecimal.valueOf(0));
        walletRepository.save(wallet);
        var jwtToken = jwtService.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtToken);
        sendReport(user, Action.CREATE, "user account created");
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        ));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        sendReport(user, Action.LOGIN, "user logged in");
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    private boolean isValidRole(String role) {
        return Role.CLIENT.name().equalsIgnoreCase(role) || Role.SELLER.name().equalsIgnoreCase(role);
    }

    private void sendReport(User user, Action action, String description) {
        UserActionRequest request = UserActionRequest.builder()
                .userId(user.getId())
                .role(user.getRole())
                .action(action)
                .description(description)
                .changeDate(LocalDateTime.now())
                .userFullName(user.getFirstName() + " " + user.getLastName())
                .build();
        userActionService.updateUserAction(request);
    }
}
