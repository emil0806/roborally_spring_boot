package com.example.roborally.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameID;

    private String boardName;

    private int numberOfPlayers;

    private int maxNumberOfPlayers;

    public Game(String boardName, int numberOfPlayers, int maxNumberOfPlayers) {
        this.gameID += 1;
        this.boardName = boardName;
        this.numberOfPlayers = numberOfPlayers;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }
}
