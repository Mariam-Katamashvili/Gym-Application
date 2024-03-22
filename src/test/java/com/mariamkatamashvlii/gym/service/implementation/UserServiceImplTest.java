package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequest;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequest;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginUserDoesNotExist() {
        String username = "nonExistingUser";
        LoginRequest loginRequest = new LoginRequest(username, "password");
        when(userRepo.findByUsername(username)).thenReturn(null);

        boolean result = userServiceImpl.login(loginRequest);

        assertFalse(result);
    }

    @Test
    void testLoginIncorrectPassword() {
        String username = "existingUser";
        String password = "wrongPassword";
        LoginRequest loginRequest = new LoginRequest(username, password);
        User user = User.builder()
                .username("existingUser")
                .password("correctPassword")
                .build();
        when(userRepo.findByUsername(username)).thenReturn(user);

        boolean result = userServiceImpl.login(loginRequest);

        assertFalse(result);
    }

    @Test
    void testLoginSuccess() {
        String username = "existingUser";
        String password = "correctPassword";
        LoginRequest loginRequest = new LoginRequest(username, password);
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        when(userRepo.findByUsername(username)).thenReturn(user);

        boolean result = userServiceImpl.login(loginRequest);

        assertTrue(result);
    }

    @Test
    void testLoginThrowsException() {
        String username = "existingUser";
        LoginRequest loginRequest = new LoginRequest(username, "password");

        when(userRepo.findByUsername(anyString())).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userServiceImpl.login(loginRequest);
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void changePassword_UserNotFound() {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest("nonExistentUser", "currPassword", "newPassword");
        when(userRepo.findByUsername("nonExistentUser")).thenReturn(null);

        userServiceImpl.changePassword(newPasswordRequest);

        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void changePassword_Successful() {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest("existentUser", "currPassword", "newPassword");
        User mockUser = new User();
        mockUser.setUsername("existentUser");
        mockUser.setPassword("currPassword");

        when(userRepo.findByUsername("existentUser")).thenReturn(mockUser);

        userServiceImpl.changePassword(newPasswordRequest);

        assertEquals("newPassword", mockUser.getPassword());
        verify(userRepo).save(mockUser);
    }

    @Test
    void changePassword_ThrowsException() {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest("nonExistentUser", "currPassword", "newPassword");
        when(userRepo.findByUsername(anyString())).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> userServiceImpl.changePassword(newPasswordRequest));

        verify(userRepo, never()).save(any(User.class));
    }
}
