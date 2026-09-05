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

    /**
     * NEW - single player by id, used by the player profile page.
     *
     * This is one path segment after /player, so it does not clash with
     * /player/{id}/players, /player/generate/{id} or /player/getTeams/{id},
     * which are all two segments.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        return playerService.getPlayer(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * FIXED: @PathVariable was missing.
     *
     * Without it Spring treats "id" as a QUERY parameter, so a call to
     * /player/5/players bound id = null, and findByTournamentId(null) quietly
     * returned an empty list. The endpoint looked like it worked and always
     * returned [].
     */
    @GetMapping("/{id}/players")
    public ResponseEntity<List<Player>> getPLayers(@PathVariable Long id){
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
    @GetMapping("getTeams/{id}")
    public List<TeamResponse> getTeams(@PathVariable Long id){
        return playerService.getTeams(id);
    }

}
