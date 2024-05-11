package com.example.salesManagement.user.userController;

import com.example.salesManagement.user.DTOs.UserResponseDto;
import com.example.salesManagement.user.DTOs.UserUpdateRequest;
import com.example.salesManagement.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user/")
public class UserController {

    private final UserService userService;

    @PutMapping("update-user/{userId}")
    public ResponseEntity<String> register(@PathVariable Long userId, @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("users")
    public ResponseEntity<List<UserResponseDto>> getUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
