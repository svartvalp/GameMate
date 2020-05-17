package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Services.IChatService;
import com.svartvalp.GameMate.Validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;


@RequestMapping(value = "/chat")
@RestController
public class ChatController {

    final Validator<Chat> chatValidator;
    final IChatService chatService;

    @Autowired
    public ChatController(Validator<Chat> chatValidator, IChatService chatService) {
        this.chatValidator = chatValidator;
        this.chatService = chatService;
    }

    @GetMapping("/games")
    public Flux<Chat> getChatsByGameIds(@RequestParam(value = "chatIds") String[] chatIds,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "20") int size) {
        return chatService.getChatsByGames(page, size ,Arrays.asList(chatIds));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Chat> createChat(@RequestBody Chat chat, Principal principal) {
        chatValidator.validate(chat, List.of("title", "timeToLive"));
        chat.setOwnerNickname(principal.getName());
        return chatService.createChat(chat);
    }

    @GetMapping("")
    public Flux<Chat> getLastChats(@RequestParam("page") int page, @RequestParam("size") int size) {
        return chatService.getLastChats(page, size);
    }

    @DeleteMapping(value = "/{chatId}")
    public Mono<Void> deleteChat(Principal principal, @PathVariable("chatId") String chatId) {
        return chatService.deleteChat(chatId, principal.getName());
    }

    @GetMapping("/all")
    public Flux<Chat> getAllChats() {
        return chatService.getAllChats();
    }
}
