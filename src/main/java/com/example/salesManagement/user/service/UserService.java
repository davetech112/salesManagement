package com.example.salesManagement.user.service;

import com.example.salesManagement.user.DTOs.UserResponseDto;
import com.example.salesManagement.user.DTOs.UserUpdateRequest;

import java.util.List;

public interface UserService {
    String updateUser(Long userId, UserUpdateRequest updateRequest);
    UserResponseDto getUser(Long userId);
    void deleteUser(Long userId);
    List<UserResponseDto> getAllUsers();
}
