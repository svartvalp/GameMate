package com.svartvalp.GameMate.Validation;

import org.junit.Assert;
import org.junit.Test;

public class BaseStringFieldCheckerTest {

    @Test
    public void checkIsEmptyTest() {
        BaseStringFieldMatcher matcher = new BaseStringFieldMatcher();
        Assert.assertEquals((matcher.checkIsEmpty(null, "field")).get(0).getMessage(), "field must not be empty");
    }

    @Test
    public void checkIsEmailTest() {
        BaseStringFieldMatcher matcher = new BaseStringFieldMatcher();
        Assert.assertEquals(matcher.checkIsEmail("aa", "email").get(0).getMessage(), "email is not an email");
        Assert.assertEquals(matcher.checkIsEmail("a1m.ru", "email").get(0).getMessage(), "email is not an email");
        Assert.assertEquals(matcher.checkIsEmail("a@maru", "email").get(0).getMessage(), "email is not an email");
        Assert.assertEquals(matcher.checkIsEmail("@mail.ru", "email").get(0).getMessage(), "email is not an email");
    }

    @Test
    public void checkSizeTest() {
        BaseStringFieldMatcher matcher = new BaseStringFieldMatcher();
        Assert.assertEquals(matcher.checkSize("aaa", "field", 4, 100).get(0).getMessage(),"field must be more than 4 characters");
        Assert.assertEquals(matcher.checkSize("aaaaaaaa", "field", 1, 6).get(0).getMessage(),"field must be less than 6 characters");
        Assert.assertEquals(matcher.checkSize("aa","field", 2, 100).size(), 0);
    }
}
