package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Exceptions.ValidationException;
import com.svartvalp.GameMate.Models.UpdatePasswordMessage;
import com.svartvalp.GameMate.Models.User;
import com.svartvalp.GameMate.Services.IUserService;
import com.svartvalp.GameMate.Validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    IUserService userService;
    Validator<User> userValidator;

    @Autowired
    public UserController(IUserService userService, Validator<User> userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> createUser(@RequestBody User user) {
        userValidator.validate(user, List.of("email", "password", "nickname"));
        return userService.createUser(user);
    }

    @PutMapping(value = "")
    public void updatePassword(@RequestBody UpdatePasswordMessage message) {
        userService.updatePassword(message.getEmail(), message.getOldPassword(), message.getNewPassword());
    }

    @DeleteMapping(value = "")
    public void deleteUserByNickname(@RequestBody User user) {
        userValidator.validate(user, List.of("email", "password", "nickname"));
        userService.deleteUser(user);
    }

    @PutMapping(value = "/verify")
    public Mono<ResponseEntity<?>> verifyUser(@RequestBody User user) {
        userValidator.validate(user, List.of("email", "password"));
        return userService.verifyUser(user).map(bool -> bool? ResponseEntity.ok("ok") : ResponseEntity.status(HttpStatus.FORBIDDEN).body("not ok"));
    }


}
