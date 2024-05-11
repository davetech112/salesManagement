package com.example.salesManagement.user.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private Long id;
    private String phoneNumber;
    @Size(min = 8, max = 12, message = "Password must be 8 to 12 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?]).*$", message = "Password must contain at least one uppercase letter and one special character")
    private String password;
    @Email
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String location;
}
