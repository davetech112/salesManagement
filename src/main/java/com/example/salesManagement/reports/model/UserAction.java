package com.example.salesManagement.reports.model;

import com.example.salesManagement.reports.enums.Action;
import com.example.salesManagement.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_action_table")
public class UserAction {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private String userFullName;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Action action;
    private String description;
    private LocalDateTime changeDate;
}
