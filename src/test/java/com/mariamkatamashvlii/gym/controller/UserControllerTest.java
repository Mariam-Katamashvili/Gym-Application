package com.mariamkatamashvlii.gym.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mariamkatamashvlii.gym.dto.userDto.LoginRequest;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequest;
import com.mariamkatamashvlii.gym.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void login_Successful() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user", "password");
        when(userService.login(loginRequest)).thenReturn(true);

        mockMvc.perform(get("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged in successfully"));

        verify(userService).login(loginRequest);
    }

    @Test
    void login_Unauthorized() throws Exception {
        LoginRequest loginRequest = new LoginRequest("user", "wrongPassword");
        when(userService.login(loginRequest)).thenReturn(false);

        mockMvc.perform(get("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        verify(userService).login(loginRequest);
    }

    @Test
    void passChange_AuthorizedAndChanged() throws Exception {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest("user", "currentPass", "newPass");
        LoginRequest loginRequest = LoginRequest.builder().username("user").password("currentPass").build();
        when(userService.login(loginRequest)).thenReturn(true);

        mockMvc.perform(put("/users/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Password Changed Successfully"));

        verify(userService).changePassword(newPasswordRequest);
    }

    @Test
    void passChange_Unauthorized() throws Exception {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest("user", "wrongPass", "newPass");
        LoginRequest loginRequest = LoginRequest.builder().username("user").password("wrongPass").build();
        when(userService.login(loginRequest)).thenReturn(false);

        mockMvc.perform(put("/users/updatePassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPasswordRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid username or password"));

        verify(userService, never()).changePassword(newPasswordRequest);
    }
}
