package com.example.pokemonsteams.service;

import com.example.pokemonsteams.exception.TeamNotFoundException;
import com.example.pokemonsteams.model.Pokemon;
import com.example.pokemonsteams.model.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private List<Team> teams;
    private PokemonService pokemonService;
    private IdGeneratorService idGeneratorService;

    public TeamService(PokemonService pokemonService, IdGeneratorService idGeneratorService) {
        this.teams = new ArrayList<>();
        this.pokemonService = pokemonService;
        this.idGeneratorService = idGeneratorService;
    }

    public List<Team> getAll() {
        if (teams.isEmpty()) {
            throw new TeamNotFoundException("No teams found!");
        }
        return teams;
    }

    public Team getByName(String username) {
        Optional<Team> result = teams.stream()
                .filter(team -> team.getUserName().equalsIgnoreCase(username))
                .findFirst();
        if (result.isEmpty()) {
            throw new TeamNotFoundException("Team with username " + username + " not found");
        }
        return result.get();
    }

    public Team getById(Long id) {
        Optional<Team> result = teams.stream()
                .filter(team -> team.getId() == id)
                .findFirst();
        if (result.isEmpty()) {
            throw new TeamNotFoundException("Team with Id " + id + " not found");
        }
        return result.get();
    }

    public Team create(Team team) {
        long teamId = idGeneratorService.generateId();
        team.setId(teamId);
        List<Pokemon> teamPokemons = new ArrayList<>();
        for (Pokemon pokemon : team.getPokemons()) {
            Pokemon retrievedPokemon = pokemonService.getByName(pokemon.getName());
            teamPokemons.add(retrievedPokemon);
        }
        team.setPokemons(teamPokemons);
        teams.add(team);
        return team;
    }
}