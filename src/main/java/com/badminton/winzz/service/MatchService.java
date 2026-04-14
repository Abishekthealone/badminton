package com.badminton.winzz.service;


import com.badminton.winzz.dto.MatchResponse;
import com.badminton.winzz.dto.ScoreRequest;
import com.badminton.winzz.dto.TeamResponse;
import com.badminton.winzz.models.*;
import com.badminton.winzz.repository.MatchRepository;
import com.badminton.winzz.repository.TeamRepository;
import com.badminton.winzz.repository.TournamentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    private final TournamentRepository tournamentRepository;

    private final TeamRepository teamRepository;

    MatchService(MatchRepository matchRepository, TournamentRepository tournamentRepository, TeamRepository teamRepository){
        this.matchRepository=matchRepository;
        this.tournamentRepository=tournamentRepository;
        this.teamRepository=teamRepository;
    }

    public List<MatchResponse> getMatches(Long tournamentId) {

        List<Match> matches = matchRepository.findByTournamentId(tournamentId);

        return matches.stream().map(match -> {

            if (match.getTeamA() == null || match.getTeamB() == null) {
                return null;
            }

            return new MatchResponse(
                    match.getId(),
                    match.getTeamA().getTeamName(),
                    match.getTeamB().getTeamName(),
                    match.getStatus().name(),
                    match.getWinner() != null ? match.getWinner().getTeamName() : null,
                    match.getScoreboard() != null ? match.getScoreboard().getTeamAScore() : null,
                    match.getScoreboard() != null ? match.getScoreboard().getTeamBScore() : null
            );

        }).filter(Objects::nonNull).toList();
    }
    public Match updateMatchScore(Match match) {
        return matchRepository.save(match);
    }


    @Transactional
    public List<MatchResponse> generateMatches(Long tournamentId) {

        if (matchRepository.existsByTournamentId(tournamentId)) {
            throw new RuntimeException("Matches already generated for this tournament");
        }

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow();

        List<Team> teams = teamRepository.findByTournamentId(tournamentId);

        if (teams.size() < 2) {
            throw new RuntimeException("Not enough teams");
        }

        List<MatchResponse> responses = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {

                Team teamA = teams.get(i);
                Team teamB = teams.get(j);

                Match match = new Match();
                match.setTournament(tournament);
                match.setTeamA(teamA);
                match.setTeamB(teamB);
                match.setRoundType(Round.GROUP);
                match.setStatus(MatchStatus.PENDING);

                if (match.getScoreboard() == null) {
                    Scoreboard scoreboard = new Scoreboard();
                    scoreboard.setMatch(match);
                    scoreboard.setTeamAScore(0);
                    scoreboard.setTeamBScore(0);
                }

                Match saved = matchRepository.save(match);

                responses.add(new MatchResponse(
                        saved.getId(),
                        teamA.getTeamName(),
                        teamB.getTeamName(),
                        saved.getStatus().name(),
                        null,   // winner
                        null,   // scoreA
                        null    // scoreB
                ));
            }
        }

        return responses;
    }



    //get scoreboard
    public List<MatchResponse> getScoreboard(Long tournamentId) {

        List<Match> matches = matchRepository.findByTournamentId(tournamentId);

        return matches.stream().map(match -> {

            Integer scoreA = 0;
            Integer scoreB = 0;

            if (match.getScoreboard() != null) {
                scoreA = match.getScoreboard().getTeamAScore();
                scoreB = match.getScoreboard().getTeamBScore();
            }

            return new MatchResponse(
                    match.getId(),
                    match.getTeamA().getTeamName(),
                    match.getTeamB().getTeamName(),
                    match.getStatus().name(),
                    match.getWinner() != null ? match.getWinner().getTeamName() : null,
                    scoreA,
                    scoreB
            );

        }).toList();
    }


    //update scoreboard
    @Transactional
    public MatchResponse updateScoreboard(ScoreRequest request) {

        Match match = matchRepository.findMatchWithDetails(request.getMatchId())
                .orElseThrow(() -> new RuntimeException("Match not found"));

        if (match.getStatus() == MatchStatus.COMPLETED) {
            throw new RuntimeException("Match already completed");
        }

        if (request.getTeamAScore() < 0 || request.getTeamBScore() < 0) {
            throw new RuntimeException("Invalid score");
        }

        Scoreboard scoreboard = match.getScoreboard();



        if (scoreboard == null) {
            scoreboard = new Scoreboard();
            scoreboard.setMatch(match);
            scoreboard.setTeamAScore(0);
            scoreboard.setTeamBScore(0);
            match.setScoreboard(scoreboard);
        }

        if (scoreboard == null) {
            throw new RuntimeException("Scoreboard not initialized");
        }

        // update scores
        scoreboard.setTeamAScore(request.getTeamAScore());
        scoreboard.setTeamBScore(request.getTeamBScore());

        // winner logic
        if (request.getTeamAScore() > request.getTeamBScore()) {
            match.setWinner(match.getTeamA());
        } else if (request.getTeamBScore() > request.getTeamAScore()) {
            match.setWinner(match.getTeamB());
        } else {
            match.setWinner(null);
        }

        match.setStatus(MatchStatus.COMPLETED);

        return new MatchResponse(
                match.getId(),
                match.getTeamA().getTeamName(),
                match.getTeamB().getTeamName(),
                match.getStatus().name(),
                match.getWinner() != null ? match.getWinner().getTeamName() : null,
                scoreboard.getTeamAScore(),
                scoreboard.getTeamBScore()
        );
    }
}
