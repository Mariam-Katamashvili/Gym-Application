package com.mariamkatamashvlii.gym.service;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequest;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequest;
import lombok.Generated;

@Generated
public interface UserService {
    boolean login(LoginRequest loginRequest);

    void changePassword(NewPasswordRequest newPasswordRequest);
}