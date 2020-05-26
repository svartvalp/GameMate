package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Exceptions.AuthenticationException;
import com.svartvalp.GameMate.Models.ChatMessage;
import com.svartvalp.GameMate.Security.PublicKeyUtils;
import com.svartvalp.GameMate.Services.IChatMessageService;
import com.svartvalp.GameMate.Validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.List;


@RequestMapping(value = "/messages")
@RestController
public class ChatMessageController {

    private final IChatMessageService chatMessageService;
    private final Validator<ChatMessage> chatMessageValidator;
    private final PublicKeyUtils publicKeyUtils;

    @Autowired
    public ChatMessageController(IChatMessageService chatMessageService, Validator<ChatMessage> chatMessageValidator, PublicKeyUtils publicKeyUtils) {
        this.chatMessageService = chatMessageService;
        this.chatMessageValidator = chatMessageValidator;
        this.publicKeyUtils = publicKeyUtils;
    }


    @GetMapping(value = "/chat/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ChatMessage> findLastMessagesByChatId(@PathVariable("chatId") String chatId,
                                                      @RequestParam(value = "size", defaultValue = "20", required = false) int size,
                                                      @RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber,
                                                      Principal principal,
                                                      @RequestHeader("publicChatKey") String publicKey) {
        var keySubjects = publicKeyUtils.verifyKeyAndReturnSubject(publicKey).split(".");
        if(principal.getName().equals(keySubjects[1]) && chatId.equals(keySubjects[0])) {
            throw new AuthenticationException("public key not valid");
        }
        return chatMessageService.findLastMessagesByChatId(chatId, size, pageNumber);
    }

    @PostMapping(value = "/chat/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ChatMessage> createMessage(@RequestBody ChatMessage message,
                                           @PathVariable("chatId") String chatId,
                                           Principal principal,
                                           @RequestHeader("publicChatKey") String publicKey) {
        var keySubjects = publicKeyUtils.verifyKeyAndReturnSubject(publicKey).split(".");
        if(principal.getName().equals(keySubjects[1]) && chatId.equals(keySubjects[0])) {
            throw new AuthenticationException("public key not valid");
        }
        chatMessageValidator.validate(message, List.of("text", "chatId"));
        message.setChatId(chatId);
        message.setUserNickName(principal.getName());
        return chatMessageService.createMessage(message);
    }
}
