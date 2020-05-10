package com.svartvalp.GameMate.Validation;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class BaseStringFieldMatcher implements StringFieldChecker {
    @Override
    public List<ValidationError> checkIsEmpty(String subject, String fieldName) {
        if(subject == null || subject.isBlank()) {
            return List.of(new ValidationError(fieldName + " must not be empty"));
        }
        return new LinkedList<>();
    }

    @Override
    public List<ValidationError> checkIsEmail(String subject, String fieldName) {
        Pattern pattern = Pattern
                .compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
        if(!pattern.matcher(subject).matches()) {
            return List.of(new ValidationError(fieldName + " is not an email"));
        }
        return new LinkedList<>();
    }

    @Override
    public List<ValidationError> checkSize(String subject, String fieldName, int minSize, int maxSize) {
        if(subject.length() < minSize) {
            return List.of(new ValidationError(fieldName + " must be more than " + minSize + " characters"));
        }
        if(subject.length() > maxSize) {
            return List.of(new ValidationError(fieldName + " must be less than " + maxSize + " characters"));
        }
        return new LinkedList<>();
    }


}
