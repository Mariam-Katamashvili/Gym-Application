package com.mariamkatamashvlii.gym.auth;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Validation {
    public void validateTrainee(Trainee trainee, User user) {
        if (trainee.getUser() == null || trainee.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Trainee must be associated with a valid user.");
        }
        if (trainee.getBirthday() == null) {
            throw new IllegalArgumentException("Date of birth is required.");
        }
        if (trainee.getAddress() == null) {
            throw new IllegalArgumentException("Address is required.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User does not exist.");
        }
    }

    public void validateTrainer(Trainer trainer, User user) {
        if (trainer.getUser() == null || trainer.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Trainee must be associated with a valid user.");
        }
        if (trainer.getSpecialization() == null) {
            throw new IllegalArgumentException("Trainer specialization is required.");
        }
        if (user == null) {
            throw new IllegalArgumentException("User does not exist.");
        }
    }
}
