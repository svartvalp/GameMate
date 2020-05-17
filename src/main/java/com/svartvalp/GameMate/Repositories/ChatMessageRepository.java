package com.svartvalp.GameMate.Repositories;

import com.svartvalp.GameMate.Models.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ChatMessageRepository extends ReactiveMongoRepository<ChatMessage, String> {
    Flux<ChatMessage> findAllByChatId(String chatId);
    Flux<ChatMessage> findAllByChatId(String chatId, Pageable pageable);
}
