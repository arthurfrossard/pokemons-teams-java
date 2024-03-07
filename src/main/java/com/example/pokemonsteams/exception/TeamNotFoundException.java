package com.example.pokemonsteams.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String s) {
        super(s);
    }
}
