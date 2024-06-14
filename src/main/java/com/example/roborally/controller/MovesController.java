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
    public ResponseEntity<Moves> getMovesByPlayerID(@PathVariable int gameID, @PathVariable int playerID) {
        Moves moves = movesRepository.findByPlayerID(playerID);
        if (moves != null && moves.getGameID() ==gameID) {
            return ResponseEntity.ok(moves);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Moves>> getAllGameMoves(@PathVariable Long gameID) {
        List<Moves> movesList = movesRepository.findByGameID(gameID);
        if (!movesList.isEmpty()) {
            return ResponseEntity.ok(movesList);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Moves> saveMoves(@PathVariable int gameID, @RequestBody Moves moves) {
        moves.setGameID(gameID); // Sæt gameID manuelt

        Moves savedMoves = movesRepository.save(moves);
        return ResponseEntity.ok(savedMoves);
    }
}