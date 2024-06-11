package com.example.roborally.repository;

import com.example.roborally.model.Moves;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovesRepository extends JpaRepository<Moves, Long> {
    Moves findByPlayerID(int playerID);
}