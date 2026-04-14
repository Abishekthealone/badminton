package com.badminton.winzz.dto;

import com.badminton.winzz.models.MatchStatus;

public class MatchResponse {

    private Long matchId;
    private String teamA;
    private String teamB;
    private String status;
    private String winner;
    private Integer teamAScore;
    private Integer teamBScore;

    public MatchResponse(){

    }

    public MatchResponse(Long matchId, String teamA, String teamB, String status, String winner, Integer teamAScore, Integer teamBScore) {
        this.matchId = matchId;
        this.teamA = teamA;
        this.teamB = teamB;
        this.status = status;
        this.winner = winner;
        this.teamAScore = teamAScore;
        this.teamBScore = teamBScore;
    }

    public Long getMatchId() {
        return matchId;
    }

    public String getTeamA() {
        return teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public String getStatus() {
        return status;
    }

    public String getWinner() {
        return winner;
    }

    public Integer getTeamAScore() {
        return teamAScore;
    }

    public Integer getTeamBScore() {
        return teamBScore;
    }
}

