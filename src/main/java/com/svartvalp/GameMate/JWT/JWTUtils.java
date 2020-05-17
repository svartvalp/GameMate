package com.svartvalp.GameMate.JWT;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class JWTUtils {
    @Value("${jwt.secretKey}")
    private String secret;

    @Value("${jwt.expirationTime.in.seconds}")
    private long expirationTime;

    public String createToken(String nickname) {
        return Jwts.builder()
                .setSubject(nickname)
                .setExpiration(new Date(Instant
                        .now()
                        .plusSeconds(expirationTime).toEpochMilli()))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String verifyTokenAndReturnSubject(String token) throws JwtException {
         return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    private Key getSecretKey() {
        if(secret.length() < 32) {
            secret = secret + " ".repeat(32 - secret.length());
        }
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
