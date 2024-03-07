package com.example.pokemonsteams.service;
import com.example.pokemonsteams.model.Pokemon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokemonService {

    public Pokemon getByName(String pokemonName) {
        pokemonName = pokemonName.toLowerCase();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(URI.create("https://pokeapi.co/api/v2/pokemon/" + pokemonName))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            Pokemon pokemon = mapper.readValue(response.body(), Pokemon.class);
            return pokemon;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
