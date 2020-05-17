package com.svartvalp.GameMate.Validation;

import com.svartvalp.GameMate.Exceptions.ValidationException;
import com.svartvalp.GameMate.Models.ChatMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ChatMessageValidatorTest {

    @Test
    public void validateTest() {
        ChatMessageValidator validator = new ChatMessageValidator(new BaseStringFieldChecker());
        Assertions.assertThrows(ValidationException.class, () -> validator.validate(new ChatMessage(), List.of("chatId")));
    }
}
