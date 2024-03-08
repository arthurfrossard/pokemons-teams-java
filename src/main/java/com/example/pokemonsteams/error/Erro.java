package com.example.pokemonsteams.error;

import lombok.Data;

@Data
public class Erro {
    private String message;

    public Erro(String message) {
        this.message = message;
    }
}
