package com.badminton.winzz.service;

import com.badminton.winzz.models.RefreshToken;
import com.badminton.winzz.models.Users;
import com.badminton.winzz.repository.CustomerUserDetailsServiceRepository;
import com.badminton.winzz.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refreshExpirationMs}")
    private Long refreshDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final CustomerUserDetailsServiceRepository customerUserDetailsServiceRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, CustomerUserDetailsServiceRepository customerUserDetailsServiceRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.customerUserDetailsServiceRepository = customerUserDetailsServiceRepository;
    }

    public RefreshToken createRefreshToken(String username){

       Users user= customerUserDetailsServiceRepository.findByUsername(username).orElseThrow(()->
            new RuntimeException("username not found"));

        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);


    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

}
