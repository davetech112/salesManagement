package com.example.salesManagement.reports.service.serviceImpl;

import com.example.salesManagement.reports.dto.UserActionRequest;
import com.example.salesManagement.reports.model.UserAction;
import com.example.salesManagement.reports.repository.UserActionRepository;
import com.example.salesManagement.reports.service.UserActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserActionServiceImpl implements UserActionService {
    private final UserActionRepository userActionRepository;

    public void updateUserAction(UserActionRequest request) {
        UserAction userAction = new UserAction();
        userAction.setUserId(request.getUserId());
        userAction.setUserFullName(request.getUserFullName());
        userAction.setRole(request.getRole());
        userAction.setAction(request.getAction());
        userAction.setDescription(request.getDescription());
        userAction.setChangeDate(request.getChangeDate());
        userActionRepository.save(userAction);
    }

    public List<UserAction> userActionList() {
        return userActionRepository.findAllByOrderByChangeDateDesc();
    }

}
