package com.example.salesManagement.user.service;

import com.example.salesManagement.user.DTOs.AuthenticationRequest;
import com.example.salesManagement.user.DTOs.AuthenticationResponse;
import com.example.salesManagement.user.DTOs.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
