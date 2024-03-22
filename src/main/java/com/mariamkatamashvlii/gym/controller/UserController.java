package com.mariamkatamashvlii.gym.controller;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequest;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequest;
import com.mariamkatamashvlii.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequest loginRequest) {
        boolean isAuthorized = userService.login(loginRequest);
        if (isAuthorized) {
            return ResponseEntity.ok("Logged in successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<String> passChange(
            @RequestBody NewPasswordRequest newPassword) {
        LoginRequest loginRequest = LoginRequest.builder()
                .username(newPassword.getUsername())
                .password(newPassword.getCurrentPass())
                .build();
        boolean isAuthorized = userService.login(loginRequest);
        if (isAuthorized) {
            userService.changePassword(newPassword);
            return ResponseEntity.ok("Password Changed Successfully");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
    }
}