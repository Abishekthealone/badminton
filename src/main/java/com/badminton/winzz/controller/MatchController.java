package com.badminton.winzz.controller;

import com.badminton.winzz.dto.MatchResponse;
import com.badminton.winzz.dto.ScoreRequest;
import com.badminton.winzz.models.Match;
import com.badminton.winzz.models.Scoreboard;
import com.badminton.winzz.service.MatchService;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.introspect.AnnotatedParameter;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService){
        this.matchService=matchService;
    }


    @GetMapping("generate/{id}")
    public List<MatchResponse> createMatch(@PathVariable Long id) {
        return matchService.generateMatches(id);
    }


    @GetMapping("/getmatches/{tournamentId}")
    public List<MatchResponse> allMatches(@PathVariable Long tournamentId) {
        return matchService.getMatches(tournamentId);
    }



    @PutMapping("/{id}/score")
    public Match updateScore(@PathVariable Long id, @RequestBody Match match) {
        match.setId(id);
        return matchService.updateMatchScore(match);
    }


    @GetMapping("/scoreboard/{id}")
    public List<MatchResponse>  getScoreboard(@PathVariable Long id){
        return matchService.getScoreboard(id);
    }


    @PutMapping("/update/scoreboard")
    public MatchResponse updateScore(@RequestBody ScoreRequest request) {
        return matchService.updateScoreboard(request);
    }

}
