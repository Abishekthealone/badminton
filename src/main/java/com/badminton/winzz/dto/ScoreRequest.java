package com.badminton.winzz.dto;

import com.badminton.winzz.models.Scoreboard;

public class ScoreRequest {


        private Long matchId;
        private int teamAScore;
        private int teamBScore;


        ScoreRequest(){

        }

    public ScoreRequest(Long matchId, int teamAScore, int teamBScore) {
        this.matchId = matchId;
        this.teamAScore = teamAScore;
        this.teamBScore = teamBScore;
    }

    public Long getMatchId() {
        return matchId;
    }

    public int getTeamAScore() {
        return teamAScore;
    }

    public int getTeamBScore() {
        return teamBScore;
    }
}
