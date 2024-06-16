package com.example.roborally.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameID;

    private String boardName;

    private int numberOfPlayers;

    private int maxNumberOfPlayers;

    private ArrayList<PlayerInfo> players;

    private int turnID;

    public Game(String boardName, int numberOfPlayers, int maxNumberOfPlayers, int turnID) {
        this.boardName = boardName;
        this.numberOfPlayers = numberOfPlayers;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        players = new ArrayList<>();
        this.turnID = turnID;
    }

    public void addPlayer(PlayerInfo playerInfo) {
        if(players == null) {
            players = new ArrayList<>();
        }
        players.add(playerInfo);
        numberOfPlayers++;
    }

    public void incrementTurnID() {
        this.turnID++;
        if(this.turnID >= this.maxNumberOfPlayers){
            this.turnID = 0;
        }
    }
}
