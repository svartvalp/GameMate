package com.svartvalp.GameMate.Validation;

import java.util.List;

public interface StringFieldMatcher {
    public List<ValidationError> checkIsEmpty(String subject, String fieldName);
    public List<ValidationError> checkIsEmail(String subject, String fieldName);
    public List<ValidationError> checkSize(String subject, String fieldName, int minSize, int maxSize);
}
