package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Models.ChatMessage;
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

    @Autowired
    public ChatMessageController(IChatMessageService chatMessageService, Validator<ChatMessage> chatMessageValidator) {
        this.chatMessageService = chatMessageService;
        this.chatMessageValidator = chatMessageValidator;
    }

    @GetMapping(value = "/chat/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ChatMessage> findLastMessagesByChatId(@PathVariable("chatId") String chatId,
                                                      @RequestParam(value = "size", defaultValue = "20", required = false) int size,
                                                      @RequestParam(value = "page", defaultValue = "0", required = false) int pageNumber) {
        return chatMessageService.findLastMessagesByChatId(chatId, size, pageNumber);
    }

    @PostMapping(value = "/chat/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ChatMessage> createMessage(@RequestBody ChatMessage message, @PathVariable("chatId") String chatId, Principal principal) {
        chatMessageValidator.validate(message, List.of("text", "chatId"));
        message.setChatId(chatId);
        message.setUserNickName(principal.getName());
        return chatMessageService.createMessage(message);
    }
    
}
