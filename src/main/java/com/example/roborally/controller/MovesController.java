
package com.example.roborally.controller;

import com.example.roborally.model.Moves;
import com.example.roborally.repository.MovesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/moves")
public class MovesController {

    private final MovesRepository movesRepository;

    public MovesController(MovesRepository movesRepository) {
        this.movesRepository = movesRepository;
    }

    @GetMapping("/{playerID}")
    public ResponseEntity<Moves> getMovesByPlayerID(@PathVariable int playerID) {
        Moves moves = movesRepository.findByPlayerID(playerID);
        if (moves != null) {
            return ResponseEntity.ok(moves);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Moves> saveMoves(@RequestBody Moves moves) {
        Moves savedMoves = movesRepository.save(moves);
        return ResponseEntity.ok(savedMoves);
    }
}
