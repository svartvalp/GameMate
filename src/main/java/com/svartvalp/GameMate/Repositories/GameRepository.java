package com.svartvalp.GameMate.Repositories;

import com.svartvalp.GameMate.Models.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GameRepository extends ReactiveMongoRepository<Game, String> {
    Mono<Game> findByName(String name);
    Flux<Game> findByNameLike(String name);
}
