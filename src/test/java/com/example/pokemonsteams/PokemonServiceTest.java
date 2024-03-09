package com.example.pokemonsteams;

import com.example.pokemonsteams.exception.PokemonNotFoundException;
import com.example.pokemonsteams.service.PokemonService;
import com.example.pokemonsteams.model.Pokemon;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebClient
public class PokemonServiceTest {

    @Autowired
    private PokemonService pokemonService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testGetExistingPokemon() throws Exception {
        String pokemonName = "bulbasaur";
        String mockResponse = "{\"id\":1,\"name\":\"bulbasaur\",\"weight\":69,\"height\":7}";

        when(restTemplate.getForObject(any(String.class), any())).thenReturn(mockResponse);

        Pokemon pokemon = pokemonService.getByName(pokemonName);
        assertEquals("bulbasaur", pokemon.getName());
        assertEquals(1, pokemon.getId());
        assertEquals(69, pokemon.getWeight());
        assertEquals(7, pokemon.getHeight());
    }

    @Test
    public void testGetNonExistingPokemon() {
        String pokemonName = "nonexistingpokemon";

        when(restTemplate.getForObject(any(String.class), any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found"));

        try {
            pokemonService.getByName(pokemonName);
        } catch (PokemonNotFoundException e) {
            assertEquals("Pokemon not found: nonexistingpokemon", e.getMessage());
        }
    }
}