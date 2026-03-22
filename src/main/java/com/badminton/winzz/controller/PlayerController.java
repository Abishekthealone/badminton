package com.badminton.winzz.controller;

import com.badminton.winzz.dto.PlayerRequest;
import com.badminton.winzz.models.Player;
import com.badminton.winzz.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService){
        this.playerService=playerService;
    }

    @GetMapping("/Players")
    public ResponseEntity<List<Player>> getPLayers(){
        List<Player> players=playerService.getPlayersList();
        return new ResponseEntity<>(players, HttpStatus.CREATED);
    }


    @PostMapping("/add")
    public ResponseEntity<String> addPlayers(@RequestBody PlayerRequest request){

        playerService.addPlayersList(request);

        return new ResponseEntity<>("Added players",HttpStatus.CREATED);
    }

}
