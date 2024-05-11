package com.example.salesManagement.reports.service;

import com.example.salesManagement.reports.dto.UserActionRequest;
import com.example.salesManagement.reports.model.UserAction;

import java.util.List;


public interface UserActionService {
    void updateUserAction(UserActionRequest request);

    List<UserAction> userActionList();
}
