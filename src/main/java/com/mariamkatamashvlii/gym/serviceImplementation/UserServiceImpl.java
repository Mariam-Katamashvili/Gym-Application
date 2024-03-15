package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import com.mariamkatamashvlii.gym.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final UsernameGenerator usernameGenerator;
    private final PasswordGenerator passwordGenerator;

    @Override
    @Transactional
    public User create(User user) {
        user.setUsername(usernameGenerator.generateUsername(user.getFirstName(), user.getLastName()));
        user.setPassword(passwordGenerator.generatePassword());
        log.info("Created user - {}", user.getUsername());
        return userRepo.create(user);
    }

    @Override
    public User update(User user) {
        User newUser = userRepo.update(user);
        log.info("Updated user - {}", user.getUsername());
        return newUser;
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepo.delete(user);
        log.info("Deleted user - {}", user.getUsername());
    }

    @Override
    public User select(long id) {
        log.info("Selecting user with id {}", id);
        return userRepo.select(id);
    }

    @Override
    public User select(String username) {
        log.info("Selecting user - {}", username);
        return userRepo.select(username);
    }

    @Override
    public List<User> findAll() {
        log.info("Returning all users");
        return userRepo.findAll();
    }

}