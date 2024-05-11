package com.example.salesManagement.reports.dto;

import com.example.salesManagement.reports.enums.Action;
import com.example.salesManagement.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserActionRequest {
    private Long userId;
    private String userFullName;
    private Role role;
    private Action action;
    private String description;
    private LocalDateTime changeDate;
}
