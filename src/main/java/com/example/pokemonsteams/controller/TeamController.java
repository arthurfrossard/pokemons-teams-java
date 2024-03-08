package com.example.pokemonsteams.controller;

import com.example.pokemonsteams.error.Erro;
import com.example.pokemonsteams.exception.PokemonNotFoundException;
import com.example.pokemonsteams.exception.TeamNotFoundException;
import com.example.pokemonsteams.model.Team;
import com.example.pokemonsteams.service.TeamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {
    Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        logger.info("getTeams method of timeFavoritoController fired.");
        try {
            List<Team> teams;
            teams = teamService.getAll();
            logger.info("getAll Method of TeamController OK.");
            return new ResponseEntity<>(teams, HttpStatus.OK);
        } catch (TeamNotFoundException e) {
            logger.error("Error getTimes method: " + new Erro(e.getMessage()));
            return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getByName(@PathVariable String userName) {
        logger.info("TeamController's getByName method fired for UserName: " + userName);
        try {
            Team team = teamService.getByName(userName);
            logger.info("TeamController OK's getByName method.");
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (TeamNotFoundException e) {
            logger.error("Error getById TimeController method: " + new Erro(e.getMessage()));
            return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByName(@PathVariable long id) {
        logger.info("TeamController's getByName method fired for ID: " + id);
        try {
            Team team = teamService.getById(id);
            logger.info("TeamController OK's getByName method.");
            return new ResponseEntity<>(team, HttpStatus.OK);
        } catch (TeamNotFoundException e) {
            logger.error("Error getById TimeController method: " + new Erro(e.getMessage()));
            return new ResponseEntity<>(new Erro(e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Team team) {
        try {
            Team createdTeam = teamService.create(team);
            logger.info("Team created successfully.");
            String message = "Equipe criada com sucesso. ID da equipe: " + createdTeam.getId();
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } catch (PokemonNotFoundException ex) {
            return new ResponseEntity<>("Pokemon not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
