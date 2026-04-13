package com.badminton.winzz.service;


import com.badminton.winzz.models.*;
import com.badminton.winzz.repository.MatchRepository;
import com.badminton.winzz.repository.TeamRepository;
import com.badminton.winzz.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Match> getMatches() {
        return matchRepository.findAll();
    }
    public Match updateMatchScore(Match match) {
        return matchRepository.save(match);
    }


    public List<Match> generateMatches(Long tournamentId) {

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow();

        List<Team> teams = teamRepository.findByTournamentId(tournamentId);

        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {

                Match match = new Match();
                match.setTournament(tournament);
                match.setTeamA(teams.get(i));
                match.setTeamB(teams.get(j));
                match.setRoundType(Round.GROUP);
                match.setStatus(MatchStatus.PENDING);

                matches.add(matchRepository.save(match));
            }
        }

        return matches;
    }
}
