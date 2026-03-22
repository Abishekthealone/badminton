package com.badminton.winzz.controller;

import com.badminton.winzz.models.Tournament;
import com.badminton.winzz.service.TournamentService;
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


    @PostMapping("/tournamentName")
    public ResponseEntity<String> addTrnName(@RequestBody Tournament trnName){

        tournamentService.addTrnName(trnName);

        return new ResponseEntity<>("Added", HttpStatus.CREATED);
    }

    @GetMapping("/tournament")
    public ResponseEntity<List<Tournament>> getTrn(){
        List<Tournament> trn=tournamentService.getTrn();
        return new ResponseEntity<>(trn,HttpStatus.OK);
    }






}
