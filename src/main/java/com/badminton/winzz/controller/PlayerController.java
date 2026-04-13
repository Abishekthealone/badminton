package com.badminton.winzz.controller;

import com.badminton.winzz.dto.PlayerRequest;
import com.badminton.winzz.dto.TeamResponse;
import com.badminton.winzz.models.Player;
import com.badminton.winzz.models.Team;
import com.badminton.winzz.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService){
        this.playerService=playerService;
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<List<Player>> getPLayers(Long id){
        List<Player> players=playerService.getAllPlayers(id);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }




    @PostMapping("/{id}/players")
    public List<Player> addPlayers(
            @PathVariable Long id,
            @RequestBody List<Player> players) {
        return playerService.addPlayersToTournament(id, players);
    }


    @GetMapping("generate/{id}")
    public List<TeamResponse> generateTeams(@PathVariable Long id){
        return playerService.generateTeams(id);
    }
    @GetMapping("Teams/{id}")
    public List<TeamResponse> getTeams(@PathVariable Long id){
        return playerService.getTeams(id);
    }

}
