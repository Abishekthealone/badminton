package com.badminton.winzz.controller;

import com.badminton.winzz.models.Tournament;
import com.badminton.winzz.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TournamentController {


    private final TournamentService tournamentService;

    TournamentController(TournamentService tournamentService){
        this.tournamentService=tournamentService;
    }


    @PostMapping("/tournaments")
    public ResponseEntity<Tournament> addTrnName(@RequestBody Tournament trnName){

        tournamentService.createTournament(trnName);

        return ResponseEntity.status(HttpStatus.CREATED).body(trnName);
    }

    @GetMapping("/tournament")
    public ResponseEntity<List<Tournament>> getTrn(){
        List<Tournament> trn=tournamentService.getAllTournaments();
        return new ResponseEntity<>(trn,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Tournament getTournament(@PathVariable Long id) {
        return tournamentService.getTournament(id);
    }






}
