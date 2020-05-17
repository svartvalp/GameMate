package com.svartvalp.GameMate.Validation;

import com.svartvalp.GameMate.Exceptions.ValidationException;
import com.svartvalp.GameMate.Models.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ChatValidator implements Validator<Chat> {

    final StringFieldChecker baseStringFieldChecker;

    @Autowired
    public ChatValidator(StringFieldChecker baseStringFieldChecker) {
        this.baseStringFieldChecker = baseStringFieldChecker;
    }

    @Override
    public void validate(Chat object, List<String> fieldsToValidate) throws ValidationException {
        List<ValidationError> errors = new LinkedList<>();
        if(fieldsToValidate.contains("title")) {
            errors.addAll(baseStringFieldChecker.checkIsEmpty(object.getTitle(), "title"));
        }
        if(fieldsToValidate.contains("timeToLive")) {
            if(object.getTimeToLive() == 0) {
                errors.add(new ValidationError("timeToLive must be not empty and more than 0"));
            }
        }
        if(errors.size() > 0) {
            throw new ValidationException("Chat validation failed", errors);
        }
    }

}
