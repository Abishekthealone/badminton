package com.badminton.winzz.filter;

import com.badminton.winzz.service.CustomUserDetailsService;
import com.badminton.winzz.util.JWTutil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilterChain extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";
    @Autowired
     private JWTutil jwTutil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


       String authHeader= request.getHeader("Authorization");

       String token=null;
        String username=null;

        boolean hasBearerToken = authHeader != null
                && authHeader.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length());

        try {
            if (hasBearerToken) {

                token = authHeader.substring(BEARER_PREFIX.length()).trim();

                username = jwTutil.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


                    UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                    //validate the token
                    if (jwTutil.validateToken(token, userDetails, username)) {

                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        //set all request related details
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        }catch (JwtException | IllegalArgumentException | UsernameNotFoundException e){
            SecurityContextHolder.clearContext();
            logger.debug("jwt exception "+e.getMessage());
        }

        //call next filter
        filterChain.doFilter(request, response);

    }
}
