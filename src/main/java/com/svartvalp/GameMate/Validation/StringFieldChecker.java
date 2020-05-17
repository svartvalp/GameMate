package com.svartvalp.GameMate.Validation;

import java.util.List;

public interface StringFieldChecker {
    List<ValidationError> checkIsEmpty(String subject, String fieldName);
    List<ValidationError> checkIsEmail(String subject, String fieldName);
    List<ValidationError> checkSize(String subject, String fieldName, int minSize, int maxSize);
}
