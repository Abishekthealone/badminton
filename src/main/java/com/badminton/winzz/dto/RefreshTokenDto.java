package com.badminton.winzz.dto;

public class RefreshTokenDto {

    private String refreshToken;

    private String username;

    public RefreshTokenDto(String refreshToken, String username) {
        this.refreshToken = refreshToken;
        this.username = username;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = username;
    }
}

