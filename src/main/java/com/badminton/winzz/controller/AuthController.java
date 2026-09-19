package com.badminton.winzz.controller;

import com.badminton.winzz.dto.AuthenticationResponseDto;
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
import org.springframework.security.core.AuthenticationException;
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
    public ResponseEntity<AuthenticationResponseDto> auth(@RequestBody SignUpDto request){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }
        catch(AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String accessToken =jwTutil.generateJwt(request.getUsername());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getUsername());


        return ResponseEntity.ok(new AuthenticationResponseDto(accessToken,refreshToken.getToken()));
    }


    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenDto request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // Generate a fresh new access token using the user profile
                    String newAccessToken = jwTutil.generateJwt(request.getUserName());;
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
                    refreshTokenRepository.delete(token);
                    return ResponseEntity.ok("Logged out successfully.");
                })
                .orElse(ResponseEntity.badRequest().body("Invalid refresh token."));
    }
}
