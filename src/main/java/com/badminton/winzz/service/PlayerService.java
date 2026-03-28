package com.badminton.winzz.service;

import com.badminton.winzz.dto.PlayerRequest;
import com.badminton.winzz.dto.TeamResponse;
import com.badminton.winzz.models.Player;
import com.badminton.winzz.models.Tournament;
import com.badminton.winzz.repository.PlayerRepository;
import com.badminton.winzz.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final TournamentRepository tournamentRepository;

    public PlayerService(PlayerRepository playerRepository, TournamentRepository tournamentRepository) {
        this.playerRepository = playerRepository;
        this.tournamentRepository = tournamentRepository;
    }

    //get players list
    public List<Player> getPlayersList() {
        return playerRepository.findAll();
    }


    //add players in tournament
    public void addPlayersList(PlayerRequest request) {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(request.getTournamentID());

        if (optionalTournament.isEmpty()) {
            throw new RuntimeException("Tournament not found");
        }


        Tournament tournament = optionalTournament.get();

        List<String> playerNames = request.getPlayerNames();

        if (playerNames.size() < 4 || playerNames.size() % 2 != 0) {
            throw new RuntimeException("Players must be even and minimum 4");
        }

        List<Player> player = playerNames.stream().map(playerName -> {
            Player p = new Player();
            p.setPlayerName(playerName);
            p.setTournament(tournament);
            return p;

        }).toList();

        playerRepository.saveAll(player);
    }

    //make teams

    public List<TeamResponse> generateTeams(Long tournamentId) {

        List<Player> players = playerRepository.findByTournamentId(tournamentId);

        if (players.size() < 4 || players.size() % 2 != 0) {
            throw new RuntimeException("Players must be even and minimum 4");
        }

        // 🔀 Shuffle players
        Collections.shuffle(players);

        List<TeamResponse> teams = new ArrayList<>();

        int teamCount = 1;

        for (int i = 0; i < players.size(); i += 2) {

            Player p1 = players.get(i);
            Player p2 = players.get(i + 1);

            String teamName = "Team-" + teamCount++;

            teams.add(new TeamResponse(
                    teamName,
                    List.of(p1.getPlayerName(), p2.getPlayerName())
            ));
        }

        return teams;
    }

}

