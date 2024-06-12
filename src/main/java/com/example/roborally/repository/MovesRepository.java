package com.example.roborally.repository;

import com.example.roborally.model.Moves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovesRepository extends JpaRepository<Moves, Long> {
    Moves findByPlayerID(int playerID);
    List<Moves> findByGameID(Long gameID); // Opdateret til at søge efter gameID som en almindelig kolonne
}
