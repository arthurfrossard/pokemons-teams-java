package com.example.pokemonsteams.controller;

import com.example.pokemonsteams.error.Erro;
import com.example.pokemonsteams.exception.PokemonNotFoundException;
import com.example.pokemonsteams.exception.TeamNotFoundException;
import com.example.pokemonsteams.model.Pokemon;
import com.example.pokemonsteams.model.Team;
import com.example.pokemonsteams.service.PokemonService;
import com.example.pokemonsteams.service.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teams")
public class TeamController {
    Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private PokemonService pokemonService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        logger.info("TeamController's getAll method fired.");
        try {
            Map<Long, Map<String, Object>> teamsMap = new HashMap<>();

            List<Team> teams = teamService.getAll();

            for (Team team : teams) {
                Map<String, Object> teamInfo = new HashMap<>();
                teamInfo.put("owner", team.getUserName());
                List<Map<String, Object>> pokemonsInfo = new ArrayList<>();

                for (Pokemon pokemon : team.getPokemons()) {
                    Map<String, Object> pokemonInfo = new HashMap<>();
                    pokemonInfo.put("id", pokemon.getId());
                    pokemonInfo.put("name", pokemon.getName());
                    pokemonInfo.put("weight", pokemon.getWeight());
                    pokemonInfo.put("height", pokemon.getHeight());
                    pokemonsInfo.add(pokemonInfo);
                }

                teamInfo.put("pokemons", pokemonsInfo);
                teamsMap.put(team.getId(), teamInfo);
            }

            return new ResponseEntity<>(teamsMap, HttpStatus.OK);
        } catch (TeamNotFoundException ex) {
            logger.error("Error getAll method: " + new Erro(ex.getMessage()));
            return new ResponseEntity<>(new Erro(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{user}")
    public ResponseEntity<?> getByUser(@PathVariable String user) {
        logger.info("TeamController's getByUser method fired for user: " + user);
        try {
            Team team = teamService.getByName(user);
            logger.info("TeamController OK's getByUser method for UserName: " + user);
            return new ResponseEntity<>(teamToResponse(team), HttpStatus.OK);
        } catch (TeamNotFoundException ex) {
            logger.error("Error getByUser method: " + new Erro(ex.getMessage()));
            return new ResponseEntity<>(new Erro(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    private Map<String, Object> teamToResponse(Team team) {
        Map<String, Object> response = new HashMap<>();
        response.put("owner", team.getUserName());
        List<Map<String, Object>> pokemonList = new ArrayList<>();
        for (Pokemon pokemon : team.getPokemons()) {
            Map<String, Object> pokemonInfo = new HashMap<>();
            pokemonInfo.put("id", pokemon.getId());
            pokemonInfo.put("name", pokemon.getName());
            pokemonInfo.put("weight", pokemon.getWeight());
            pokemonInfo.put("height", pokemon.getHeight());
            pokemonList.add(pokemonInfo);
        }
        response.put("pokemons", pokemonList);
        return response;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, Object> requestBody) {
        try {
            String userName = (String) requestBody.get("userName");
            List<String> pokemonNames = (List<String>) requestBody.get("pokemons");

            List<Pokemon> pokemons = new ArrayList<>();
            for (String pokemonName : pokemonNames) {
                Pokemon pokemon = pokemonService.getByName(pokemonName);
                pokemons.add(pokemon);
            }

            Team team = new Team();
            team.setUserName(userName);
            team.setPokemons(pokemons);

            Team createdTeam = teamService.create(team);
            logger.info("Team created successfully.");
            String message = "Team created successfully. Team ID: " + createdTeam.getId();
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (PokemonNotFoundException ex) {
            return new ResponseEntity<>("Pokemon not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating team: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}