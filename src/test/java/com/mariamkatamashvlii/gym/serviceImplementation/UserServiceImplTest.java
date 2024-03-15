package com.mariamkatamashvlii.gym.serviceImplementation;

import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.generator.PasswordGenerator;
import com.mariamkatamashvlii.gym.generator.UsernameGenerator;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepo;
    @Mock
    private UsernameGenerator usernameGenerator;
    @Mock
    private PasswordGenerator passwordGenerator;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");

        String generatedUsername = "john.doe";
        String generatedPassword = "securepassword";

        when(usernameGenerator.generateUsername(user.getFirstName(), user.getLastName())).thenReturn(generatedUsername);
        when(passwordGenerator.generatePassword()).thenReturn(generatedPassword);
        when(userRepo.create(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertEquals(generatedUsername, createdUser.getUsername());
        assertEquals(generatedPassword, createdUser.getPassword());
        verify(userRepo).create(user);
        verify(usernameGenerator).generateUsername(user.getFirstName(), user.getLastName());
        verify(passwordGenerator).generatePassword();
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("johndoe");
        user.setPassword("password");

        when(userRepo.update(any(User.class))).thenReturn(user);

        User updatedUser = userService.update(user);

        assertNotNull(updatedUser, "The updated user should not be null.");
        verify(userRepo).update(userCaptor.capture());
        assertEquals(user.getUsername(), updatedUser.getUsername(), "The username should match the expected value.");
        assertEquals(user.getPassword(), updatedUser.getPassword(), "The password should match the expected value.");
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("johndoe");

        doNothing().when(userRepo).delete(any(User.class));

        userService.delete(user);

        verify(userRepo).delete(user);
    }

    @Test
    void testSelectById() {
        long id = 1L;
        User expectedUser = new User();
        expectedUser.setUserId(id);

        when(userRepo.select(id)).thenReturn(expectedUser);

        User actualUser = userService.select(id);

        assertEquals(expectedUser, actualUser);
        verify(userRepo).select(id);
    }

    @Test
    void testSelectByUsername() {
        String username = "johndoe";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        when(userRepo.select(username)).thenReturn(expectedUser);

        User actualUser = userService.select(username);

        assertEquals(expectedUser, actualUser);
        verify(userRepo).select(username);
    }

    private final ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

}
