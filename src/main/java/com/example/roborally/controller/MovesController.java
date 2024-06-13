package com.example.roborally.controller;

import com.example.roborally.model.Moves;
import com.example.roborally.repository.MovesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lobby/{gameID}/moves")
public class MovesController {

    private final MovesRepository movesRepository;

    public MovesController(MovesRepository movesRepository) {
        this.movesRepository = movesRepository;
    }

    @GetMapping("/{playerID}")
    public ResponseEntity<String> getMovesByPlayerID(@PathVariable int gameID, @PathVariable int playerID) {
        Moves moves = movesRepository.findByPlayerID(playerID);
        if (moves != null && moves.getGameID() == gameID) {
            String response = moves.getPlayerID() + "," + moves.getGameID() + "," + moves.getChosenMoves() + ";";
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<String> getAllGameMoves(@PathVariable int gameID) {
        List<Moves> movesList = movesRepository.findByGameID((long) gameID);
        if (!movesList.isEmpty()) {
            StringBuilder responseBuilder = new StringBuilder();
            for (Moves moves : movesList) {
                responseBuilder.append(moves.getPlayerID())
                        .append(",")
                        .append(moves.getGameID())
                        .append(",")
                        .append(moves.getChosenMoves())
                        .append(";");
            }
            // Fjern sidste semikolon
            String response = responseBuilder.substring(0, responseBuilder.length() - 1);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Moves> saveMoves(@PathVariable int gameID, @RequestBody String stringMoves) {
        String[] movesArr = stringMoves.split(":");
        Moves savedMoves = new Moves(Integer.parseInt(movesArr[0]), Integer.parseInt(movesArr[1]), movesArr[2]);
        movesRepository.save(savedMoves);
        return ResponseEntity.ok(savedMoves);
    }
}