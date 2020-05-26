package com.svartvalp.GameMate.Security;


import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class PublicKeyUtils {

    public String createPublicKey(String subject) {
        return Base64.getUrlEncoder().encodeToString(subject.getBytes(StandardCharsets.UTF_8));
    }

    public String verifyKeyAndReturnSubject(String publicKey) {
        return new String(Base64.getDecoder().decode(publicKey));
    }
}
