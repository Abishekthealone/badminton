package com.badminton.winzz.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String playerName;

    private Integer ranking;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Player() {}

    public Player(Long id, String playerName, Integer ranking) {
        this.id = id;
        this.playerName = playerName;
        this.ranking = ranking;
    }

    public Long getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getRanking() {
        return ranking;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Team getTeam() {
        return team;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}