package com.badminton.winzz.dto;

import java.util.List;

public class TeamResponse {

    private List<String> Players;

    private String TeamName;

    public TeamResponse(String teamName,List<String> players) {
        Players = players;
        TeamName = teamName;
    }

    public List<String> getPlayers() {
        return Players;
    }

    public String getTeamName() {
        return TeamName;
    }
}
