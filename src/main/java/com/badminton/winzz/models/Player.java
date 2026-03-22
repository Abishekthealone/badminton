package com.badminton.winzz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Enter the players name")
    private String PlayerName;

    @ManyToOne
    @JoinColumn(name="tournament_id")
    private Tournament tournament;

    public Player(Long id, String playerName, Tournament tournament) {
        this.id = id;
        this.PlayerName = playerName;
        this.tournament = tournament;
    }

    public Player(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }
}
