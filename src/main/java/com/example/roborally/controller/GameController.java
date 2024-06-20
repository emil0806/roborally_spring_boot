package com.example.roborally.controller;

import com.example.roborally.model.Game;
import com.example.roborally.model.Moves;
import com.example.roborally.model.PlayerInfo;
import com.example.roborally.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class GameController {
    private GameRepository gameRepository;
    public ArrayList<Game> games = new ArrayList<>();

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

    public Game findGame(int gameID) {
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
    public ResponseEntity<String> setTurnID(@PathVariable int gameID) {
        Game game = findGame(gameID);
        game.incrementTurnID();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/lobby/{id}/allUsersReady")
    public ResponseEntity<Boolean> areAllUsersReady(@PathVariable int id) {
        Game game = findGame(id);
        if (game != null) {
            return ResponseEntity.ok(game.getTurnID() == 0);
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

    @PostMapping("/lobby/{gameID}/moves")
    public ResponseEntity<String> saveMoves(@PathVariable int gameID, @RequestBody Moves moves) {
        Game game = findGame(gameID);
        game.addMoves(moves);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/lobby/{gameID}/moves")
    public ResponseEntity<ArrayList<Moves>> getAllMoves(@PathVariable int gameID) {
        Game game = findGame(gameID);
        ArrayList<Moves> listOfMoves = new ArrayList<>(game.getMoves());

        return ResponseEntity.ok().body(listOfMoves);
    }
    @GetMapping(value = "/lobby/{gameID}/{playerID}/getStartSpace")
    public ResponseEntity<Double> getStartSpace(@PathVariable int gameID, @PathVariable int playerID) {
        Game game = findGame(gameID);
        return ResponseEntity.ok(game.getPlayers().get(playerID).getStartSpace());
    }

    @PostMapping("/lobby/{gameID}/setAvailableStartSpaces")
    public ResponseEntity<String> setAvailableStartSpaces(@PathVariable int gameID, @RequestBody String startSpace) {
        Game game = findGame(gameID);
        game.deleteStartSpace(Double.valueOf(startSpace));
        return ResponseEntity.ok("ok");
    }

    @GetMapping(value = "/lobby/{gameID}/getAvailableStartSpaces")
    public ResponseEntity<ArrayList<Double>> getAvailableStartSpaces(@PathVariable int gameID) {
        Game game = findGame(gameID);
        return ResponseEntity.ok(game.getStartPlace());
    }

    @GetMapping(value = "/lobby/{gameID}/getRemovedStartingPlace")
    public ResponseEntity<ArrayList<Double>>getRemovedStartingPlace (@PathVariable int gameID) {
        Game game = findGame(gameID);
        return ResponseEntity.ok(game.getDeletedStartPlace());
    }

    @GetMapping("/lobby/{gameID}/allPlayersChosen")
    public ResponseEntity<Boolean> allPlayersChosen(@PathVariable int gameID){
        Game game = findGame(gameID);
        if(game.getMaxNumberOfPlayers() == game.getMoves().size()){
            return ResponseEntity.ok(true);
        } else{
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/lobby/{gameID}/moves/{playerID}")
    public ResponseEntity<ArrayList<String>> getMovesByPlayerID(@PathVariable int gameID, @PathVariable int playerID){
        Game game = findGame(gameID);
        ArrayList<String> playerMoves = new ArrayList<>();
        for(int i = 0; i < game.getMoves().size(); i++) {
            if(game.getMoves().get(i).getPlayerID() == playerID) {
                playerMoves = game.getMoves().get(i).getChosenMoves();
            }
        }
        return ResponseEntity.ok(playerMoves);
    }

    @PostMapping("/lobby/{gameID}/moves/{playerID}/{step}/setInteraction")
    public ResponseEntity<String> setInteraction(@PathVariable int gameID, @PathVariable int playerID, @PathVariable int step, @RequestBody String interaction) {
        Game game = findGame(gameID);
        for(int i = 0; i < game.getMoves().size(); i++) {
            if(game.getMoves().get(i).getPlayerID() == playerID) {
                game.getMoves().get(i).getChosenMoves().set(step, interaction);
            }
        }
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/lobby/{gameID}/moves/{playerID}/waitForInteraction/{step}")
    public ResponseEntity<Boolean> waitForInteraction(@PathVariable int gameID, @PathVariable int playerID, @PathVariable int step) {
        Game game = findGame(gameID);
        for(Moves moves : game.getMoves()) {
            if(moves.getPlayerID() == playerID) {
                if(!(moves.getChosenMoves().get(step).equals("Left OR Right"))) {
                    return ResponseEntity.ok(true);

                } else {
                    return ResponseEntity.ok(false);
                }
            }
        }
        return ResponseEntity.ok(false);
    }

    @DeleteMapping("/lobby/{gameID}/clearAllMoves")
    public ResponseEntity<String> clearAllMoves(@PathVariable int gameID) {
        Game game = findGame(gameID);
        if(game.getNumberOfPlayers() == game.getPlayersReady()) {
            game.clearMoves();
            game.setPlayersReady(0);
        }
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/lobby/{gameID}/playersReady")
    public ResponseEntity<String> setPlayersReady(@PathVariable int gameID, @RequestBody String playersReady) {
        Game game = findGame(gameID);
        game.setPlayersReady(Integer.parseInt(playersReady));
        return ResponseEntity.ok("OK");
    }

    @PutMapping("/lobby/{gameID}/playersReady")
    public ResponseEntity<String> incrementPlayersReady(@PathVariable int gameID) {
        Game game = findGame(gameID);
        game.incrementPlayersReady();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/lobby/{gameID}/playersReady")
    public ResponseEntity<Boolean> areAllPlayersReady(@PathVariable int gameID) {
        Game game = findGame(gameID);
        if(game.getPlayersReady() == game.getNumberOfPlayers()) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

//    @GetMapping("/lobby/{gameID}/round")
//    public ResponseEntity<Integer> getRoundNumber(@PathVariable int gameID){
//        Game game = findGame(gameID);
//        return ResponseEntity.ok(game.getRound());
//    }
//
//    @PutMapping("/lobby/{gameID}/round")
//    public ResponseEntity<String> incrementRoundNumber(@PathVariable int gameID) {
//        Game game = findGame(gameID);
//        game.incrementRound();
//        return ResponseEntity.ok("OK");
//    }
//
//    @PutMapping("/lobby/{gameID}/{playerID}/round")
//    public ResponseEntity<String> incrementPlayerRoundNumber(@PathVariable int gameID, @PathVariable int playerID) {
//        Game game = findGame(gameID);
//        game.getPlayers().get(playerID).incrementPlayerRound();
//        return ResponseEntity.ok("OK");
//    }
//
//    @GetMapping("/lobby/{gameID}/round/allReady")
//    public ResponseEntity<Boolean> allPlayersAtSameRound(@PathVariable int gameID){
//        Game game = findGame(gameID);
//        Boolean allAtSameRound = true;
//        for(int i = 0; i < game.getPlayers().size(); i++){
//            if(game.getPlayers().get(i).getRound() != game.getRound()){
//                allAtSameRound = false;
//            }
//        }
//        return ResponseEntity.ok(allAtSameRound);
//    }
}