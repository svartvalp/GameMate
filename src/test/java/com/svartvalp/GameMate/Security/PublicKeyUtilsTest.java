package com.svartvalp.GameMate.Security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PublicKeyUtilsTest {

    @Test
    public void testCreatingAndVerifying() {
        PublicKeyUtils utils = new PublicKeyUtils();
        String publicKey = utils.createPublicKey("subject");
        assertEquals("subject", utils.verifyKeyAndReturnSubject(publicKey));
    }

}