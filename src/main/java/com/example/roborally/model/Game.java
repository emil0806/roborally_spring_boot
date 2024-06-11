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
    private int gameID;

    private String gameJson;
}
