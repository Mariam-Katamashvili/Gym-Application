package com.mariamkatamashvlii.gym.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PasswordGenerator {
    private final Random random = new Random();
    public String generatePassword() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            char randomChar = (char) random.nextInt(33, 127);
            builder.append(randomChar);
        }
        return builder.toString();
    }
}
