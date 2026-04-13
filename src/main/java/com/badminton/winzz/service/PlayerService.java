package com.badminton.winzz.service;


import com.badminton.winzz.dto.TeamResponse;
import com.badminton.winzz.models.*;
import com.badminton.winzz.repository.MatchRepository;
import com.badminton.winzz.repository.PlayerRepository;
import com.badminton.winzz.repository.TeamRepository;
import com.badminton.winzz.repository.TournamentRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final TournamentRepository tournamentRepository;

    private final TeamRepository teamRepository;

    private final MatchRepository matchRepository;


    PlayerService(PlayerRepository playerRepository,TournamentRepository tournamentRepository,TeamRepository teamRepository,MatchRepository matchRepository){
        this.playerRepository=playerRepository;
        this.tournamentRepository=tournamentRepository;
        this.teamRepository=teamRepository;
        this.matchRepository=matchRepository;
    }


    // ADD players into tournament
    public List<Player> addPlayersToTournament(Long tournamentId, @NonNull List<Player> players) {

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        if (players.size() < 4)
            throw new IllegalArgumentException("At least 4 players required");

        for (Player p : players) {
            p.setTournament(tournament);
        }

        return playerRepository.saveAll(players);
    }



    public List<Player> getAllPlayers(Long id) {
        return playerRepository.findByTournamentId(id);
    }


    //
    // shuffle player make them to team
    @Transactional
    public List<TeamResponse> generateTeams(Long tournamentId) {



        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow();

        playerRepository.clearTeamFromPlayers(tournamentId);
        teamRepository.deleteByTournamentId(tournamentId);



        List<Player> players = playerRepository.findByTournamentId(tournamentId);

        if (players.size() < 4 || players.size() % 2 != 0)
            throw new RuntimeException("Players must be even and minimum 4");

        Collections.shuffle(players);

        List<TeamResponse> teams = new ArrayList<>();

        int teamCount = 1;

        for (int i = 0; i < players.size(); i += 2) {

            Team team = new Team();
            team.setTeamName("Team " + teamCount++);
            team.setTournament(tournament);

            teamRepository.save(team);

            Player p1 = players.get(i);
            Player p2 = players.get(i + 1);

            p1.setTeam(team);
            p2.setTeam(team);

            playerRepository.saveAll(List.of(p1, p2));

            teams.add(new TeamResponse(
                    team.getTeamName(),
                    List.of(p1.getPlayerName(), p2.getPlayerName())
            ));
        }

        return teams;
    }


    public List<TeamResponse> getTeams(Long id) {

        List<Team> teams= teamRepository.findAllByTournamentId(id);

        return teams.stream().map(team->new TeamResponse(team.getTeamName(),team.getPlayers().stream().map(Player::getPlayerName).toList())).toList();
    }
}

