package com.badminton.winzz.service;

import com.badminton.winzz.models.Tournament;
import com.badminton.winzz.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    //add the tournament
    public void addTrnName(Tournament trnName){
        tournamentRepository.save(trnName);
    }

    public List<Tournament> getTrn(){
        return tournamentRepository.findAll();
    }





}
