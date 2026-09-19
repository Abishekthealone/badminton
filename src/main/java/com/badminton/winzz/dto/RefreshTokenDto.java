package com.badminton.winzz.dto;

public class RefreshTokenDto {

    private String refreshToken;

    private String userName;

    public RefreshTokenDto(String refreshToken, String userName) {
        this.refreshToken = refreshToken;
        this.userName = userName;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

