package com.badminton.winzz.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;


    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    public Team() {}

    public Team(Long id, String teamName, Tournament tournament, List<Player> players) {
        this.id = id;
        this.teamName = teamName;
        this.tournament = tournament;
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}