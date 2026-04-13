package com.badminton.winzz.controller;

import com.badminton.winzz.models.Match;
import com.badminton.winzz.service.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService){
        this.matchService=matchService;
    }


    @PostMapping("matches/{id}")
    public List<Match> createMatch(@PathVariable Long tournamentId) {
        return matchService.generateMatches(tournamentId);
    }


    @GetMapping
    public List<Match> allMatches() {
        return matchService.getMatches();
    }

    @PutMapping("/{id}/score")
    public Match updateScore(@PathVariable Long id, @RequestBody Match match) {
        match.setId(id);
        return matchService.updateMatchScore(match);
    }

}
