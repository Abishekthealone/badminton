package com.badminton.winzz.service;

import com.badminton.winzz.dto.PlayerRequest;
import com.badminton.winzz.models.Player;
import com.badminton.winzz.models.Tournament;
import com.badminton.winzz.repository.PlayerRepository;
import com.badminton.winzz.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    private final TournamentRepository tournamentRepository;

    public PlayerService (PlayerRepository playerRepository,TournamentRepository tournamentRepository){
        this.playerRepository=playerRepository;
        this.tournamentRepository=tournamentRepository;
    }

    public List<Player> getPlayersList(){
        return playerRepository.findAll();
    }

    public void addPlayersList(PlayerRequest request){
       Optional<Tournament> optionalTournament= tournamentRepository.findById(request.getTournamentID());

       if(optionalTournament.isEmpty()){
           throw new RuntimeException("Tournament not found");
       }


        Tournament tournament = optionalTournament.get();

      List<String> playerNames= request.getPlayerNames();

        if(playerNames.size() < 4 || playerNames.size() % 2 != 0){
            throw new RuntimeException("Players must be even and minimum 4");
        }

        List<Player> player= playerNames.stream().map(playerName->{
            Player p=new Player();
            p.setPlayerName(playerName);
            p.setTournament(tournament);
            return p;

        }).toList();

    playerRepository.saveAll(player);
       }

    }



