package com.example.pokemonsteams;

import com.example.pokemonsteams.exception.TeamNotFoundException;
import com.example.pokemonsteams.model.Pokemon;
import com.example.pokemonsteams.model.Team;
import com.example.pokemonsteams.service.IdGeneratorService;
import com.example.pokemonsteams.service.PokemonService;
import com.example.pokemonsteams.service.TeamService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;

    @Mock
    private PokemonService pokemonService;

    @Mock
    private IdGeneratorService idGeneratorService;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllTeams() {
        assertThrows(TeamNotFoundException.class, () -> {
            teamService.getAll();
        });
    }

    @Test
    public void testGetTeamByName() {
        Pokemon mockPokemon = new Pokemon();
        mockPokemon.setId(25);
        mockPokemon.setName("pikachu");
        mockPokemon.setWeight(60);
        mockPokemon.setHeight(4);
        when(pokemonService.getByName("pikachu")).thenReturn(mockPokemon);

        when(idGeneratorService.generateId()).thenReturn(1L);

        Team team = new Team();
        team.setUserName("ash");
        List<Pokemon> pokemons = new ArrayList<>();
        Pokemon pokemon = new Pokemon();
        pokemon.setName("pikachu");
        pokemons.add(pokemon);
        team.setPokemons(pokemons);

        Team createdTeam = teamService.create(team);

        Team retrievedTeam = teamService.getByName("ash");
        assertEquals("ash", retrievedTeam.getUserName());
        assertEquals(1, retrievedTeam.getId());
        assertEquals(1, retrievedTeam.getPokemons().size());
        assertEquals("pikachu", retrievedTeam.getPokemons().get(0).getName());
        assertEquals(25, retrievedTeam.getPokemons().get(0).getId());
    }

    @Test
    public void testGetTeamByName_NotFound() {
        assertThrows(TeamNotFoundException.class, () -> {
            teamService.getByName("unknown");
        });
    }

    @Test
    public void testCreateTeam() {
        Pokemon mockPokemon = new Pokemon();
        mockPokemon.setId(25);
        mockPokemon.setName("pikachu");
        mockPokemon.setWeight(60);
        mockPokemon.setHeight(4);
        when(pokemonService.getByName("pikachu")).thenReturn(mockPokemon);

        when(idGeneratorService.generateId()).thenReturn(2L);

        Team team = new Team();
        team.setUserName("ash");
        List<Pokemon> pokemons = new ArrayList<>();
        Pokemon pokemon = new Pokemon();
        pokemon.setName("pikachu");
        pokemons.add(pokemon);
        team.setPokemons(pokemons);

        Team createdTeam = teamService.create(team);

        assertEquals(2L, createdTeam.getId());
        assertEquals("ash", createdTeam.getUserName());
        assertEquals(1, createdTeam.getPokemons().size());
        assertEquals("pikachu", createdTeam.getPokemons().get(0).getName());
        assertEquals(25, createdTeam.getPokemons().get(0).getId());
        assertEquals(60, createdTeam.getPokemons().get(0).getWeight());
        assertEquals(4, createdTeam.getPokemons().get(0).getHeight());
    }
}