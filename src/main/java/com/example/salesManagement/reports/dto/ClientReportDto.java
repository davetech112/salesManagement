package com.example.salesManagement.reports.dto;

import com.example.salesManagement.user.DTOs.ClientDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientReportDto {
    private long totalNumberOfClients;
    private ClientDto topSpendingClient;
}
