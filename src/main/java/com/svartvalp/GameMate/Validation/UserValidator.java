package com.svartvalp.GameMate.Validation;

import com.svartvalp.GameMate.Exceptions.ValidationException;
import com.svartvalp.GameMate.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class UserValidator implements Validator<User> {
    private final StringFieldChecker baseStringFieldChecker;

    @Autowired
    public UserValidator(StringFieldChecker baseStringFieldChecker) {
        this.baseStringFieldChecker = baseStringFieldChecker;
    }

    @Override
    public void validate(User object, List<String> fieldsToValidate) throws ValidationException {
        List<ValidationError> errors = new LinkedList<>();
        if(fieldsToValidate.contains("email")) {
           errors.addAll(validateEmail(object));
        }
        if(fieldsToValidate.contains("nickname")) {
            errors.addAll(validateNickName(object));
        }
        if(fieldsToValidate.contains("password")) {
            errors.addAll(validatePassword(object));
        }
        if(errors.size() > 0) {
            throw new ValidationException("User validation failed.", errors);
        }
    }

    public List<ValidationError> validateEmail(User user) {
        List<ValidationError> errors = new LinkedList<>();
        errors.addAll(baseStringFieldChecker.checkIsEmpty(user.getEmail(), "email"));
        if(errors.size() == 0) {
            errors.addAll(baseStringFieldChecker.checkIsEmail(user.getEmail(), "email"));
        }
        return errors;
    }
    public List<ValidationError> validateNickName(User user) {
        List<ValidationError> errors = new LinkedList<>();
        errors.addAll(baseStringFieldChecker.checkIsEmpty(user.getNickname(), "nickname"));
        if(errors.size() == 0) {
            errors.addAll(baseStringFieldChecker.checkSize(user.getNickname(), "nickname", 6, 255));
        }
        return errors;
    }
    public List<ValidationError> validatePassword(User user) {
        List<ValidationError> errors = new LinkedList<>();
        errors.addAll(baseStringFieldChecker.checkIsEmpty(user.getPassword(), "password"));
        if(errors.size() == 0) {
            errors.addAll(baseStringFieldChecker.checkSize(user.getPassword(), "password", 6, 255));
        }
        return errors;
    }

}
