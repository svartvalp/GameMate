package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Game;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IGameService {
    public Mono<Game> createGame(Game game);
    public Mono<Void> deleteGame(Game game);
    public Flux<Game> findGamesLike(String name);
    public Flux<Game>  findAllGames();
}
