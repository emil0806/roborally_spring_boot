package com.example.roborally.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PlayerInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long playerID;

    String name;

    String age;
}
