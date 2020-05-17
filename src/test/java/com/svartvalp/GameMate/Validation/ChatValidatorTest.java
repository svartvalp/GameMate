package com.svartvalp.GameMate.Validation;

import com.svartvalp.GameMate.Exceptions.ValidationException;
import com.svartvalp.GameMate.Models.Chat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ChatValidatorTest {

    @Test
    public void validateTest() {
        ChatValidator validator = new ChatValidator(new BaseStringFieldChecker());
        var chat = new Chat();
        Assertions.assertThrows(ValidationException.class,() -> validator.validate(chat, List.of("title", "timeToLive")));
    }
}
