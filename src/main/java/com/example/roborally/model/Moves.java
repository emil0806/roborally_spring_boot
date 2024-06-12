package com.example.roborally.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "moves")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Moves {

    @Id
    private int playerID;
    private Long gameID;

    @Column(name = "chosen_moves")
    private String chosenMoves;

}