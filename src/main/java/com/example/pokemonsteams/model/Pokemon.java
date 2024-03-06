package com.example.pokemonsteams.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    private int id;
    private List<Ability> abilities;
    private int base_experience;
    private String name;
    private List<Move> moves;
    private int weight;
}
