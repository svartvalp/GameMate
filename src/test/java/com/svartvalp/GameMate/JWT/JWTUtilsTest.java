package com.svartvalp.GameMate.JWT;

import com.svartvalp.GameMate.Validation.StringFieldChecker;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



public class JWTUtilsTest {

    @Test
    public void creatingTokenTest() {
        JWTUtils jwtUtils = new JWTUtils();
        jwtUtils.setSecret("secret");
        jwtUtils.setExpirationTime(300);
        String token = jwtUtils.createToken("nickname");
        Assertions.assertEquals("nickname", jwtUtils.verifyTokenAndReturnSubject(token));
    }

    @Test
    public void testExpiration() throws InterruptedException {
        JWTUtils jwtUtils = new JWTUtils();
        jwtUtils.setSecret("secret");
        jwtUtils.setExpirationTime(2);
        String token = jwtUtils.createToken("nickname");
        Thread.sleep(2000);
        Assertions.assertThrows(JwtException.class,() -> jwtUtils.verifyTokenAndReturnSubject(token));
    }

}
