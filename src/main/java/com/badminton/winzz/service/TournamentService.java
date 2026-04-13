package com.badminton.winzz.service;

import com.badminton.winzz.models.Tournament;
import com.badminton.winzz.models.TournamentStatus;
import com.badminton.winzz.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public void createTournament(Tournament t) {
        t.setStatus(TournamentStatus.PLANNED);
       tournamentRepository.save(t);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElseThrow();
    }






}
