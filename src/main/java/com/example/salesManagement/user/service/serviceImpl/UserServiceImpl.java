package com.example.salesManagement.user.service.serviceImpl;

import com.example.salesManagement.exception.NotFoundException;
import com.example.salesManagement.reports.dto.UserActionRequest;
import com.example.salesManagement.reports.enums.Action;
import com.example.salesManagement.reports.service.UserActionService;
import com.example.salesManagement.user.DTOs.UserResponseDto;
import com.example.salesManagement.user.DTOs.UserUpdateRequest;
import com.example.salesManagement.user.Model.User;
import com.example.salesManagement.user.service.UserService;
import com.example.salesManagement.user.userRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserActionService userActionService;

    public String updateUser(Long userId, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(updateRequest.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        modelMapper.map(updateRequest, user);
        userRepository.save(user);
        sendReport(user, Action.UPDATE, "user updated");
        return "User updated successfully";
    }

    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserResponseDto response = new UserResponseDto();
        modelMapper.map(user, response);
        return response;
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Modifying
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.deleteById(user.getId());
        sendReport(user, Action.DELETE, "user account deleted");
    }

    private void sendReport(User user, Action action, String description) {
        UserActionRequest request = UserActionRequest.builder()
                .userId(user.getId())
                .role(user.getRole())
                .action(action)
                .description(description)
                .changeDate(LocalDateTime.now())
                .userFullName(user.getFirstName() + " " + user.getLastName())
                .build();
        userActionService.updateUserAction(request);
    }
}
