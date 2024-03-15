package com.mariamkatamashvlii.gym.auth;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationTest {
    private Validation validation;
    private User validUser;
    private Trainee validTrainee;
    private Trainer validTrainer;

    @BeforeEach
    void setUp() {
        validation = new Validation();
        validUser = new User();
        validUser.setUserId(1L);

        validTrainee = new Trainee();
        validTrainee.setUser(validUser);
        validTrainee.setBirthday(new Date(System.currentTimeMillis()));
        validTrainee.setAddress("123 Main St");

        validTrainer = new Trainer();
        validTrainer.setUser(validUser);
    }

    @Test
    void validateTrainee_Valid() {
        assertDoesNotThrow(() -> validation.validateTrainee(validTrainee, validUser));
    }

    @Test
    void validateTrainee_NullUser() {
        validTrainee.setUser(null);
        assertThrows(IllegalArgumentException.class, () -> validation.validateTrainee(validTrainee, null));
    }

    @Test
    void validateTrainee_NullBirthday() {
        validTrainee.setBirthday(null);
        assertThrows(IllegalArgumentException.class, () -> validation.validateTrainee(validTrainee, validUser));
    }

    @Test
    void validateTrainee_NullAddress() {
        validTrainee.setAddress(null);
        assertThrows(IllegalArgumentException.class, () -> validation.validateTrainee(validTrainee, validUser));
    }

    @Test
    void validateTrainer_NullUser() {
        validTrainer.setUser(null);
        assertThrows(IllegalArgumentException.class, () -> validation.validateTrainer(validTrainer, null));
    }

    @Test
    void validateTrainer_NullSpecialization() {
        validTrainer.setSpecialization(null);
        assertThrows(IllegalArgumentException.class, () -> validation.validateTrainer(validTrainer, validUser));
    }
}
