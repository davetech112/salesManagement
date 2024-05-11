package com.example.salesManagement.user.service.serviceImpl;

import com.example.salesManagement.reports.service.UserActionService;
import com.example.salesManagement.user.DTOs.UserUpdateRequest;
import com.example.salesManagement.user.Model.User;
import com.example.salesManagement.user.userRepository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserActionService userActionService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testUpdateUser() {

        Long userId = 1L;
        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setId(userId);
        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        doNothing().when(modelMapper).map(updateRequest, user);

        String result = userService.updateUser(userId, updateRequest);

        verify(userRepository).save(user);

        // Assert
        assertEquals("User updated successfully", result);
    }


}