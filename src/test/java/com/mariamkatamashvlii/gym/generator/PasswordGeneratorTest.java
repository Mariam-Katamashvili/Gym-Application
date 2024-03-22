package com.mariamkatamashvlii.gym.generator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordGeneratorTest {
    @Test
    void generatePassword_LengthIs10() {
        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generatePassword();
        assertEquals(10, password.length(), "The generated password should be 10 characters long.");
    }

    @Test
    void generatePassword_CharactersInRange() {
        PasswordGenerator generator = new PasswordGenerator();
        String password = generator.generatePassword();
        password.chars().forEach(c -> assertTrue(c >= 33 && c <= 126,
                "Each character in the generated password should be within the ASCII range 33 to 126."));
    }

    @Test
    void generatePassword_Randomness() {
        PasswordGenerator generator = new PasswordGenerator();
        String password1 = generator.generatePassword();
        String password2 = generator.generatePassword();
        assertNotEquals(password1, password2, "Two consecutively generated passwords should not be the same.");
    }
}

