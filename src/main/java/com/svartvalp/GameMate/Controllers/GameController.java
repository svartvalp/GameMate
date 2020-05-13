package com.svartvalp.GameMate.Controllers;

import com.svartvalp.GameMate.Models.Game;
import com.svartvalp.GameMate.Services.GameService;
import com.svartvalp.GameMate.Services.IGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/game")
@RestController
public class GameController {

    IGameService gameService;

    @Autowired
    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping(value = "")
    public Flux<Game> findAllGames() {
        return gameService.findAllGames();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Game> createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }
}
