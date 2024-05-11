package com.example.salesManagement.user.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class RegisterRequest {
    private String phoneNumber;
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 12, message = "Password must be 8 to 12 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>?]).*$", message = "Password must contain at least one uppercase letter and one special character")
    private String password;
    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String address;
    private String location;
}
