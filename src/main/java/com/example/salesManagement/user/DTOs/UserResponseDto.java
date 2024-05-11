package com.example.salesManagement.user.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String location;
}
