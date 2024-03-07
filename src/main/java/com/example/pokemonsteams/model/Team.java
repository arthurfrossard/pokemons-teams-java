package com.example.pokemonsteams.model;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private String userName;
    private List<Pokemon> pokemons;
}
