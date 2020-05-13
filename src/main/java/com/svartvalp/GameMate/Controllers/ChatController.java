package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Services.ChatService;
import com.svartvalp.GameMate.Services.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;


@RequestMapping(value = "/chat")
@RestController
public class ChatController {

    IChatService chatService;

    @Autowired
    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/games")
    public Flux<Chat> getChatsByGameIds(@RequestParam(value = "chatIds") String[] chatIds,
                                        @RequestParam(value = "page", defaultValue = "1") int page,
                                        @RequestParam(value = "size", defaultValue = "20") int size) {
        return chatService.getChatsByGames(page, size ,Arrays.asList(chatIds));
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Chat> createChat(@RequestBody Chat chat) {
        return chatService.createChat(chat);
    }

    @GetMapping("")
    public Flux<Chat> getLastChats(@RequestParam("page") int page, @RequestParam("size") int size) {
        return chatService.getLastChats(page, size);
    }
    @GetMapping("/all")
    public Flux<Chat> getAllChats() {
        return chatService.getAllChats();
    }
}
