package com.example.roborally.controller;

import com.example.roborally.model.Game;
import com.example.roborally.model.PlayerInfo;
import com.example.roborally.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    private GameRepository gameRepository;
    public ArrayList<Game> games = new ArrayList<>();
    public Game game = new Game();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @PostMapping("/lobby")
    public ResponseEntity<Game> uploadGame(@RequestBody Game game){
        try {
            games.add(game);
            Game newGame = gameRepository.save(game);
            return ResponseEntity.ok(newGame);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/lobby/{id}")
    public ResponseEntity<Game> getGameByID(@PathVariable int id) {
        Game game = findGame(id);
        return ResponseEntity.ok(game);
    }


    @PostMapping("/lobby/{id}")
    public ResponseEntity<String> joinGame(@PathVariable int id, @RequestBody PlayerInfo playerInfo) {
        Game game = findGame(id);
        game.addPlayer(playerInfo);
        return ResponseEntity.ok("OK");
    }

    private Game findGame(int gameID) {
        return games.stream()
                .filter(s -> s.getGameID() == gameID)
                .findFirst()
                .orElse(null);
    }

    @GetMapping(value = "/lobby")
    public ResponseEntity<ArrayList<Game>> listOfGames() {

        return ResponseEntity.ok().body(games);
    }

    @GetMapping(value = "/lobby/{gameID}/players")
    public ResponseEntity<ArrayList<PlayerInfo>> getPlayers(@PathVariable int gameID) {
        Game game = findGame(gameID);
        ArrayList<PlayerInfo> listOfPlayers = new ArrayList<>(game.getPlayers());
        return ResponseEntity.ok().body(listOfPlayers);
    }

    @DeleteMapping(value = "/lobby/{id}/players")
    public ResponseEntity<String> removePlayer(@PathVariable int gameID, @PathVariable int playerID) {
        Game game = findGame(gameID);

        game.getPlayers().remove(playerID);

        return ResponseEntity.ok().body("OK");
    }

    @GetMapping(value = "/lobby/{id}/getNumberOfPlayers")
    public ResponseEntity<Integer> getNumOfPlayers(@PathVariable int id) {
        Game game = findGame(id);
        return ResponseEntity.ok(game.getNumberOfPlayers());
    }

    @GetMapping(value = "/lobby/{id}/getMaxNumberOfPlayers")
    public ResponseEntity<Integer> getMaxNumOfPlayers(@PathVariable int id) {
        Game game = findGame(id);
        return ResponseEntity.ok(game.getMaxNumberOfPlayers());
    }

    @GetMapping(value = "/lobby/{id}/getTurnID")
    public ResponseEntity<Integer> getTurnID(@PathVariable int id) {
        Game game = findGame(id);
        return ResponseEntity.ok(game.getTurnID());
    }
    @PostMapping("/lobby/{gameID}/setTurnID")
    public ResponseEntity<String> setTurnID(@PathVariable int gameID, @RequestBody String turn) {
        Game game = findGame(gameID);
        game.incrementTurnID();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/lobby/{id}/allUsersReady")
    public ResponseEntity<Boolean> areAllUsersReady(@PathVariable int id) {
        Game game = findGame(id);
        if (game != null) {
            if (game.getTurnID() == game.getMaxNumberOfPlayers()) {
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.ok(false);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @PostMapping("/lobby/{gameID}/{playerID}/setStartSpace")
    public ResponseEntity<String> setTurnID(@PathVariable int gameID, @PathVariable int playerID, @RequestBody String startSpace) {
        Game game = findGame(gameID);
        game.getPlayers().get(playerID).setStartSpace(Double.parseDouble(startSpace));
        return ResponseEntity.ok("OK");
    }

}
