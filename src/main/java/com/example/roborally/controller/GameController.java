package com.example.roborally.controller;

import com.example.roborally.model.Game;
import com.example.roborally.repository.GameRepository;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    private GameRepository gameRepository;
    public ArrayList<Game> games = new ArrayList<>();
    public Game game = new Game();

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostMapping("/lobby")
    public ResponseEntity<String> uploadGame(@RequestBody String gameString){
        String[] gameSplitString = gameString.split(",");
        Game game = new Game(gameSplitString[0], Integer.parseInt(gameSplitString[1]), Integer.parseInt(gameSplitString[2]));
        games.add(game);
        gameRepository.save(game);
        return ResponseEntity.ok(game.getGameID() + ", "+ game.getBoardName() + ", " + game.getNumberOfPlayers() + ", " + game.getMaxNumberOfPlayers());
    }

    @GetMapping("/lobby/{id}")
    public ResponseEntity<String> getGameByID(@PathVariable int id) {
        Game game = findGame(id);

        return ResponseEntity.ok().body(game.getGameID() + ", "+ game.getBoardName() + ", " + game.getNumberOfPlayers() + ", " + game.getMaxNumberOfPlayers());
    }

    @PostMapping("/lobby/{id}")
    public ResponseEntity<String> joinGame(@PathVariable int id) {

        return ResponseEntity.ok().body("hello");
    }

    private Game findGame(int gameID) {
        return games.stream()
                .filter(s -> s.getGameID() == gameID)
                .findFirst()
                .orElse(null);
    }

    @GetMapping(value = "/lobby")
    public ResponseEntity<String> listOfGames() {
        String listOfGames = "";

        for(Game game : games) {
          String gameInfo = game.getGameID() + "," + game.getBoardName() + "," + game.getNumberOfPlayers() + "," + game.getMaxNumberOfPlayers() + ";";
          listOfGames += gameInfo;
        }

        return ResponseEntity.ok().body(listOfGames);
    }

}
