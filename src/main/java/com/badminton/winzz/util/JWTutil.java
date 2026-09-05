package com.badminton.winzz.util;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;



@Component
public class JWTutil {

    private final String SECRET_KEY="my-super-secret-key-my-super-secret-key-12345";


    private final SecretKey key =
            Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateJwt(String username){

       return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 15))
        .signWith(key).compact();
    }

    public String extractUsername(String token){

        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token){
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails, String username) {
        try {

            return username.equals(userDetails.getUsername()) && !isExpiredToken(token);
        }catch (JwtException e){
            return false;
        }
    }

    private boolean isExpiredToken(String token) {

            return extractClaims(token).getExpiration().before(new Date());
    }
}
