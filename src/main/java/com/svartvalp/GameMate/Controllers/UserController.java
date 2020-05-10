package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Exceptions.ValidationException;
import com.svartvalp.GameMate.Models.UpdatePasswordMessage;
import com.svartvalp.GameMate.Models.User;
import com.svartvalp.GameMate.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/user")
public class UserController {

    IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping(value = "")
    public void updatePassword(@RequestBody UpdatePasswordMessage message) {
        userService.updatePassword(message.getEmail(), message.getOldPassword(), message.getNewPassword());
    }

    @DeleteMapping(value = "")
    public void deleteUserByNickname(@RequestBody User user) {
        userService.deleteUser(user);
    }

    @PutMapping(value = "/verify")
    public Mono<ResponseEntity<?>> verifyUser(@RequestBody User user) {
        return userService.verifyUser(user).map(bool -> bool? ResponseEntity.ok("ok") : ResponseEntity.status(HttpStatus.FORBIDDEN).body("not ok"));
    }
}
