package com.badminton.winzz.dto;

import com.badminton.winzz.models.Player;

import java.util.List;

public class PlayerRequest {

    private Long tournamentID;

    private List<String> PlayerNames;

    public Long getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(Long tournamentID) {
        this.tournamentID = tournamentID;
    }

    public List<String> getPlayerNames() {
        return PlayerNames;
    }

    public void setPlayerNames(List<String> playerNames) {
        PlayerNames = playerNames;
    }
}
