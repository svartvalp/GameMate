package com.svartvalp.GameMate.Validation;

import com.svartvalp.GameMate.Exceptions.ValidationException;
import com.svartvalp.GameMate.Models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ChatMessageValidator implements Validator<ChatMessage> {
    final StringFieldChecker baseStringFieldChecker;

    @Autowired
    public ChatMessageValidator(StringFieldChecker baseStringFieldChecker) {
        this.baseStringFieldChecker = baseStringFieldChecker;
    }

    @Override
    public void validate(ChatMessage object, List<String> fieldsToValidate) throws ValidationException {
        List<ValidationError> errors = new LinkedList<>();
        if(fieldsToValidate.contains("text")) {
            errors.addAll(baseStringFieldChecker.checkIsEmpty(object.getText(), "text"));
            if(errors.size() == 0)
                errors.addAll(baseStringFieldChecker.checkSize(object.getText(), "text", 0, 255));
        }
        if(fieldsToValidate.contains("chatId")) {
            errors.addAll(baseStringFieldChecker.checkIsEmpty(object.getChatId(), "chatId"));
        }
        if(errors.size() > 0) {
            throw new ValidationException("Chat message validation failed", errors);
        }
    }
}
