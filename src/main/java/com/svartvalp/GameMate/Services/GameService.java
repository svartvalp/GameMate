package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Game;
import com.svartvalp.GameMate.Repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GameService implements IGameService {

    GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Mono<Game> createGame(Game game) {
        return gameRepository.insert(game);
    }

    @Override
    public Mono<Void> deleteGame(Game game) {
        return gameRepository.delete(game);
    }

    @Override
    public Flux<Game> findGameLike(String name) {
        return gameRepository.findByNameLike(name);
    }

    @Override
    public Flux<Game> findAllGames() {
        return gameRepository.findAll();
    }
}
