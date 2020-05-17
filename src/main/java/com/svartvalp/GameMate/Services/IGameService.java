package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGameService {
    Mono<Game> createGame(Game game);
    Mono<Void> deleteGame(Game game);
    Flux<Game> findGamesLike(String name);
    Flux<Game>  findAllGames();
}
