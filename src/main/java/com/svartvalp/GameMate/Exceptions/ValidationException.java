package com.svartvalp.GameMate.Exceptions;

import com.svartvalp.GameMate.Validation.ValidationError;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationException extends RuntimeException {
    public List<ValidationError> errors = new LinkedList<>();
    public ValidationException() {
        super("incoming request can't pass through validation");
    }

    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(String message, List<ValidationError> errors){
        super(message);
        this.errors = errors;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " Errors: " +
                errors.stream().map(ValidationError::getMessage).collect(Collectors.joining(";"));
    }
}
