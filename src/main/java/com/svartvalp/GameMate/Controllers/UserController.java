package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.JWT.JWTUtils;
import com.svartvalp.GameMate.Models.UpdatePasswordMessage;
import com.svartvalp.GameMate.Models.User;
import com.svartvalp.GameMate.Services.IUserService;
import com.svartvalp.GameMate.Validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    IUserService userService;
    Validator<User> userValidator;
    JWTUtils jwtUtils;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserValidator(Validator<User> userValidator) {
        this.userValidator = userValidator;
    }

    @Autowired
    public void setJwtUtils(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> createUser(@RequestBody User user) {
        userValidator.validate(user, List.of("email", "password", "nickname"));
        return userService.createUser(user);
    }

    @PutMapping(value = "")
    public void updatePassword(@RequestBody UpdatePasswordMessage message) {
        userService.updatePassword(message.getEmail(), message.getOldPassword(), message.getNewPassword());
    }

    @DeleteMapping(value = "")
    public void deleteUserByNickname(@RequestBody User user, Principal principal) {
        userValidator.validate(user, List.of("email", "password", "nickname"));
        if(principal.getName().equals(user.getNickname())) {
            userService.deleteUser(user);
        }
    }

    @PutMapping(value = "/verify")
    public Mono<Map<String, String>> verifyUser(@RequestBody User user) {
        userValidator.validate(user, List.of("email", "password"));
        return userService.verifyUser(user).flatMap(verifiedUser -> {
            Map<String, String> body = new HashMap<>();
            body.put("token", jwtUtils.createToken(user.getNickname()));
            return Mono.just(body);
        });
    }

    @GetMapping(value = "/{nickname}")
    public Mono<User> find(@PathVariable("nickname") String nickname) {
        return userService.findUserByNickName(nickname);
    }


}
