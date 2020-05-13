package com.svartvalp.GameMate.Repositories;

import com.svartvalp.GameMate.Models.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatMessageRepository extends ReactiveMongoRepository<ChatMessage, String> {
    public Flux<ChatMessage> findAllByChatId(String chatId);
    public Flux<ChatMessage> findAllByChatId(String chatId, Pageable pageable);
}
