package com.example.roborally.controller;

import com.example.roborally.model.Game;
import com.example.roborally.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class GameController {
    private GameRepository gameRepository;
    public ArrayList<Game> games = new ArrayList<>();
    public Game game = new Game();

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostMapping("/lobby/{id}")
    public ResponseEntity<String> uploadGame(@PathVariable int id, @RequestBody String gameJson){
        Game game = new Game(id, gameJson);
        games.add(game);
        return ResponseEntity.ok(game.getGameJson());
    }

    @GetMapping("/lobby/{id}")
    public ResponseEntity<String> getGameByID(@PathVariable int id) {
        Game game = findGame(id);

        return ResponseEntity.ok().body(game.getGameJson());
    }

    private Game findGame(int gameID) {
        return games.stream()
                .filter(s -> s.getGameID() == gameID)
                .findFirst()
                .orElse(null);
    }

}
