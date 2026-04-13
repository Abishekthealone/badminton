package com.badminton.winzz.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

    @JsonManagedReference
    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();
    public Tournament(){

    }

    public Tournament(Long id, String name, TournamentStatus status, List<Team> teams) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.teams = teams;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TournamentStatus getStatus() {
        return status;
    }

    public void setStatus(TournamentStatus status) {
        this.status = status;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
