package com.example.pokemonsteams.model;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private String nomeDeUsuario;
    private List<Pokemon> pokemons;
}
