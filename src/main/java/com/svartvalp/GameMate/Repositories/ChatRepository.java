package com.svartvalp.GameMate.Repositories;

import com.svartvalp.GameMate.Models.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {
    Flux<Chat> findAllByIdIn(List<String> chatIds);
    Flux<Chat> findAllByIdExists(boolean exists, Pageable pageable);
    Flux<Chat>  findAllByGameIdsIsContaining(List<String> gameIds, Pageable pageable);
}
