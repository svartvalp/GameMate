package com.svartvalp.GameMate.Repositories;

import com.svartvalp.GameMate.Models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Mono<User> findByNickname(String nickname);
    Mono<User> findByEmail(String email);
}
