package com.svartvalp.GameMate.Validation;

import com.svartvalp.GameMate.Exceptions.ValidationException;
import com.svartvalp.GameMate.Models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserValidatorTest {
    @Autowired
    Validator<User> userValidator;

    @Test
    public void testValidationThrowingException() {
        User user = new User("email", "nick", "password");
        assertThrows(ValidationException.class, () -> userValidator.validate(user, List.of("nickname")));
    }

    @Test
    public void testValidationNotThrowException() {
        User user = new User("email", "nick", "password");
        assertDoesNotThrow(() -> userValidator.validate(user, List.of("password")));
    }
}
