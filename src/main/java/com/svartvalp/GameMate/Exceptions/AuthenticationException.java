package com.svartvalp.GameMate.Exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException() {
        super("authentication not succeeded");
    }
}
