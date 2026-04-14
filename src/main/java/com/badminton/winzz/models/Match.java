package com.badminton.winzz.models;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Enumerated(EnumType.STRING)
    private Round roundType;

    @ManyToOne
    @JoinColumn(name = "team_a_id")
    private Team teamA;

    @ManyToOne
    @JoinColumn(name = "team_b_id")
    private Team teamB;

    @ManyToOne
    @JoinColumn(name = "winner_team_id")
    private Team winner;

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private MatchStatus status;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private Scoreboard scoreboard;


    public Match(Long id, Tournament tournament, Round roundType, Team teamA, Team teamB, Team winner, LocalDateTime scheduledAt, MatchStatus status, Scoreboard scoreboard) {
        this.id = id;
        this.tournament = tournament;
        this.roundType = roundType;
        this.teamA = teamA;
        this.teamB = teamB;
        this.winner = winner;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.scoreboard = scoreboard;
    }

    public Match(){

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Round getRoundType() {
        return roundType;
    }

    public void setRoundType(Round roundType) {
        this.roundType = roundType;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }
}
