package com.badminton.winzz.controller;

import com.badminton.winzz.dto.AuthenticationResponseDto;
import com.badminton.winzz.dto.LoginInRequest;
import com.badminton.winzz.dto.RefreshTokenDto;
import com.badminton.winzz.dto.SignUpDto;
import com.badminton.winzz.models.RefreshToken;
import com.badminton.winzz.repository.RefreshTokenRepository;
import com.badminton.winzz.service.RefreshTokenService;
import com.badminton.winzz.util.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    @PostMapping("/token")
    public ResponseEntity<AuthenticationResponseDto> auth(@RequestBody LoginInRequest request) {

        RefreshToken refreshToken;
        String accessToken;
        Authentication authentication;

        try {
            // Validate credentials using Spring Security AuthenticationManager
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));


        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (authentication.isAuthenticated()) {
             accessToken = jwTutil.generateJwt(request.getUsername());
             refreshToken = refreshTokenService.createRefreshToken(request.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }

        return ResponseEntity.ok(new AuthenticationResponseDto(accessToken, refreshToken.getToken()));
    }



    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenDto request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)  // Verify token not expired
                .map(RefreshToken::getUser)                  // Get the user associated with this refresh token
                .map(user -> {
                    // Generate a fresh new access token using the user's username
                    String newAccessToken = jwTutil.generateJwt(user.getUsername());

                    // Return new access token with the same refresh token
                    // (Frontend can keep using the same refresh token for future refreshes)
                    return ResponseEntity.ok(new AuthenticationResponseDto(newAccessToken, requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody RefreshTokenDto request) {
        String requestToken = request.getRefreshToken();

        if (requestToken == null || requestToken.isBlank()) {
            return ResponseEntity.badRequest().body("Refresh token is required.");
        }

        return refreshTokenRepository.findByToken(requestToken)
                .map(token -> {
                    // Delete the refresh token from database
                    // Now this token can never be used again
                    refreshTokenRepository.delete(token);
                    return ResponseEntity.ok("Logged out successfully.");
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }
}
