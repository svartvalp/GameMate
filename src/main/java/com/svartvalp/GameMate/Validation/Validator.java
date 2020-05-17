package com.svartvalp.GameMate.Validation;

import com.svartvalp.GameMate.Exceptions.ValidationException;

import java.util.List;

public interface Validator<T> {
    void validate(T object, List<String> fieldsToValidate) throws ValidationException;
}
