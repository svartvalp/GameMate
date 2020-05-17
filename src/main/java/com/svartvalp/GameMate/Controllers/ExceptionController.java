package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Exceptions.AuthenticationException;
import com.svartvalp.GameMate.Exceptions.ResourceNotFoundException;
import com.svartvalp.GameMate.Exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ResourceNotFoundException.class, ValidationException.class, AuthenticationException.class})
    public ResponseEntity<String> handleErrors(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
