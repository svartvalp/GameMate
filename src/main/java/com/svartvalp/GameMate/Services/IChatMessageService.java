package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.ChatMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IChatMessageService {
    public Flux<ChatMessage> findAllMessagesByChatId(String chatId);
    public Flux<ChatMessage> findLastMessagesByChatId(String chatId, int limit, int offset);
    public Mono<ChatMessage> createMessage(ChatMessage message);
    public void deleteMessage(ChatMessage message);
}
