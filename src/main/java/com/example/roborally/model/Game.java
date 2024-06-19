package com.example.roborally.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
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

    private ArrayList<Moves> moves;

    private int turnID;

    private ArrayList<Double> startPlace = new ArrayList<>(Arrays.asList(0.3, 0.6, 1.1, 1.4, 1.5, 1.8));

    private ArrayList<Double> deletedStartPlace = new ArrayList<>();

    private int playersReady = 0;

    public Game(String boardName, int numberOfPlayers, int maxNumberOfPlayers, int turnID) {
        this.boardName = boardName;
        this.numberOfPlayers = numberOfPlayers;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
        players = new ArrayList<>();
        this.turnID = turnID;
    }

    public void addMoves(Moves playerMoves) {
        if(moves == null) {
            moves = new ArrayList<>();
        }
        moves.add(playerMoves);
    }

    public void addPlayer(PlayerInfo playerInfo) {
        if(players == null) {
            players = new ArrayList<>();
        }
        players.add(playerInfo);
        numberOfPlayers++;
    }

    public void deleteStartSpace(Double removePlace){
        deletedStartPlace.add(removePlace);
        startPlace.remove(removePlace);
    }

    public void incrementTurnID() {
        this.turnID++;
        if(this.turnID >= this.maxNumberOfPlayers){
            this.turnID = 0;
        }
    }
    public void clearMoves() {
        this.moves = new ArrayList<>();
    }

    public void incrementPlayersReady() {
        this.playersReady++;
    }
}
