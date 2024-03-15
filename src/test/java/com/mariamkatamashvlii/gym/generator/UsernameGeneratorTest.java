package com.mariamkatamashvlii.gym.generator;

import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UsernameGeneratorTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsernameGenerator usernameGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateUsername_Unique() {
        String first = "John";
        String last = "Doe";
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        String username = usernameGenerator.generateUsername(first, last);

        assertEquals("John.Doe", username);
    }

    @Test
    void generateUsername_DuplicateOnce() {
        String first = "John";
        String last = "Doe";
        User existingUser = new User();
        existingUser.setUsername("John.Doe");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(existingUser));

        String username = usernameGenerator.generateUsername(first, last);

        assertEquals("John.Doe1", username);
    }

    @Test
    void generateUsername_MultipleDuplicates() {
        String first = "John";
        String last = "Doe";
        User existingUser1 = new User();
        existingUser1.setUsername("John.Doe");
        User existingUser2 = new User();
        existingUser2.setUsername("John.Doe1");
        List<User> existingUsers = Arrays.asList(existingUser1, existingUser2);
        when(userRepository.findAll()).thenReturn(existingUsers);

        String username = usernameGenerator.generateUsername(first, last);

        assertEquals("John.Doe2", username);
    }
}
