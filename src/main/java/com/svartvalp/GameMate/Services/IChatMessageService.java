package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.ChatMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IChatMessageService {
    Flux<ChatMessage> findAllMessagesByChatId(String chatId);
    Flux<ChatMessage> findLastMessagesByChatId(String chatId, int limit, int offset);
    Mono<ChatMessage> createMessage(ChatMessage message);
    void deleteMessage(ChatMessage message);
}
