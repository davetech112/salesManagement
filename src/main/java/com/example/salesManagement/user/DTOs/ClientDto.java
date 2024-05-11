package com.example.salesManagement.user.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long clientId;
    private String firstName;
    private String lastName;
    private String location;
    private BigDecimal totalSpend;
}
