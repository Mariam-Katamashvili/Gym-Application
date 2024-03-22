package com.mariamkatamashvlii.gym.service.implementation;

import com.mariamkatamashvlii.gym.dto.userDto.LoginRequest;
import com.mariamkatamashvlii.gym.dto.userDto.NewPasswordRequest;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;

    @Override
    @Transactional
    public boolean login(LoginRequest loginRequest) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Transaction started for login operation", transactionId);

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        try {
            User user = userRepo.findByUsername(username);
            if (user == null) {
                log.info("[{}] Username {} does not exist", transactionId, username);
                return false;
            }
            if (user.getPassword().equals(password)) {
                log.info("[{}] User {} logged in successfully", transactionId, username);
                return true;
            } else {
                log.info("[{}] Password for {} is incorrect", transactionId, username);
                return false;
            }
        } catch (Exception e) {
            log.error("[{}] Error during login for user {}: {}", transactionId, username, e.getMessage());
            throw e;
        } finally {
            log.info("[{}] Transaction ended for login operation", transactionId);
        }
    }

    @Override
    public void changePassword(NewPasswordRequest newPasswordRequest) {
        String transactionId = UUID.randomUUID().toString();
        log.info("[{}] Starting password change operation for user: {}", transactionId, newPasswordRequest.getUsername());
        try {
            User user = userRepo.findByUsername(newPasswordRequest.getUsername());
            if (user == null) {
                log.warn("[{}] No user found with username: {}", transactionId, newPasswordRequest.getUsername());
                return;
            }

            user.setPassword(newPasswordRequest.getNewPass());
            userRepo.save(user);

            log.info("[{}] Password changed successfully for user: {}", transactionId, newPasswordRequest.getUsername());
        } catch (Exception e) {
            log.error("[{}] Error changing password for user: {}. Error: {}", transactionId, newPasswordRequest.getUsername(), e.getMessage());
            throw e;
        }
    }

}