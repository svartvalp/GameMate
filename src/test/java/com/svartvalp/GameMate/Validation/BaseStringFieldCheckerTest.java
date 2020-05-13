package com.svartvalp.GameMate.Validation;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseStringFieldCheckerTest {

    @Test
    public void checkIsEmptyTest() {
        BaseStringFieldChecker matcher = new BaseStringFieldChecker();
        assertEquals((matcher.checkIsEmpty(null, "field")).get(0).getMessage(), "field must not be empty");

    }

    @Test
    public void checkIsEmailTest() {
        BaseStringFieldChecker matcher = new BaseStringFieldChecker();
        assertEquals(matcher.checkIsEmail("aa", "email").get(0).getMessage(), "email is not an email");
        assertEquals(matcher.checkIsEmail("a1m.ru", "email").get(0).getMessage(), "email is not an email");
        assertEquals(matcher.checkIsEmail("a@maru", "email").get(0).getMessage(), "email is not an email");
        assertEquals(matcher.checkIsEmail("@mail.ru", "email").get(0).getMessage(), "email is not an email");
    }

    @Test
    public void checkSizeTest() {
        BaseStringFieldChecker matcher = new BaseStringFieldChecker();
        assertEquals(matcher.checkSize("aaa", "field", 4, 100).get(0).getMessage(),"field must be more than 4 characters");
        assertEquals(matcher.checkSize("aaaaaaaa", "field", 1, 6).get(0).getMessage(),"field must be less than 6 characters");
        assertEquals(matcher.checkSize("aa","field", 2, 100).size(), 0);
    }
}
