package com.example.pokemonsteams.model;

import com.example.pokemonsteams.service.IdGeneratorService;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private long id;
    private String userName;
    private List<Pokemon> pokemons;

    public Team() {
    }

}


