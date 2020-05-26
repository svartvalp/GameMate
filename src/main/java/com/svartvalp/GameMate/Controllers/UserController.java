package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Exceptions.AuthenticationException;
import com.svartvalp.GameMate.JWT.JWTUtils;
import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Models.ChatWIthPublicKeyMessage;
import com.svartvalp.GameMate.Models.UpdatePasswordMessage;
import com.svartvalp.GameMate.Models.User;
import com.svartvalp.GameMate.Security.PublicKeyUtils;
import com.svartvalp.GameMate.Services.IChatService;
import com.svartvalp.GameMate.Services.IUserService;
import com.svartvalp.GameMate.Validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    IUserService userService;
    IChatService chatService;
    Validator<User> userValidator;
    JWTUtils jwtUtils;
    PublicKeyUtils publicKeyUtils;

    @Autowired
    public void setPublicKeyUtils(PublicKeyUtils publicKeyUtils) {
        this.publicKeyUtils = publicKeyUtils;
    }

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

    @Autowired
    public void setChatService(IChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String,String>> createUser(@RequestBody User user) {
        userValidator.validate(user, List.of("email", "password", "nickname"));
        return userService.createUser(user).map(createdUser -> {
            Map<String, String> body = new HashMap<>();
            body.put( "token",jwtUtils.createToken(user.getNickname()));
            return body;
        });
    }

    @PutMapping(value = "/password")
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

    @GetMapping(value = "/chats")
    public Flux<ChatWIthPublicKeyMessage> getUserChats(Principal principal) {
        return chatService.getUserChatsByNickname(principal.getName()).map(chat ->
                new ChatWIthPublicKeyMessage(chat,
                        publicKeyUtils.createPublicKey(chat.getId() + "." + principal.getName())));
    }

    @PutMapping(value = "/chats/{chatId}/accept", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> acceptUserSubscribing(Principal principal,
                                            @PathVariable("chatId") String chatId,
                                            @RequestBody User user) {
        userValidator.validate(user, List.of("nickname"));
        return userService.addChatToUser(user.getNickname(), chatId, principal.getName());
    }

    @GetMapping(value = "/chats/{chatId}")
    public Mono<List<String>> getUsersRequests(Principal principal, @PathVariable("chatId") String chatId) {
        return chatService.getChatById(chatId).flatMap(chat -> {
            if(chat.getOwnerNickname().equals(principal.getName())) {
                return Mono.just(chat.getUserRequests());
            }
            return Mono.error(new AuthenticationException("you have so permissions"));
        });
    }
}
