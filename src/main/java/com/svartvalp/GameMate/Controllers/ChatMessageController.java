package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Exceptions.ResourceNotFoundException;
import com.svartvalp.GameMate.Models.ChatMessage;
import com.svartvalp.GameMate.Services.IChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequestMapping(value = "/chat/messages")
@RestController
public class ChatMessageController {

    private IChatMessageService chatMessageService;

    @Autowired
    public ChatMessageController(IChatMessageService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @GetMapping(value = "/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ChatMessage> findLastMessagesByChatId(@PathVariable("chatId") String chatId,
                                                      @RequestParam(value = "size", defaultValue = "20", required = false) int size,
                                                      @RequestParam(value = "number", defaultValue = "0", required = false) int pageNunber) {
        return chatMessageService.findLastMessagesByChatId(chatId, size, pageNunber);
    }

    @PostMapping(value = "/{chatId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ChatMessage> createMessage(@RequestBody ChatMessage message, @PathVariable("chatId") String chatId) {
        message.setChatId(chatId);
        return chatMessageService.createMessage(message);
    }
    
}
