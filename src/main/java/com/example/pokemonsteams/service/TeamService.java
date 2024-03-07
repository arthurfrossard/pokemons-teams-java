package com.example.pokemonsteams.service;

import com.example.pokemonsteams.exception.TeamNotFoundException;
import com.example.pokemonsteams.model.Team;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private List<Team> teams;
    public TeamService() {
        this.teams = new ArrayList<>();
    }

    public List<Team> getAll() {
        if (teams.isEmpty()) {
            throw new TeamNotFoundException("No teams found!");
        }
        return teams;
    }

    public List<Team> getByName(String username) {
        List<Team> result = teams.stream()
                .filter(team -> team.getUserName().toLowerCase().contains(username.toLowerCase()))
                .collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new TeamNotFoundException("team with username "+ username  + " not found");
        }
        return result;
    }

    public Team create(Team team) {
        teams.add(team);
        return team;
    }

}
