package com.badminton.winzz.service;

import com.badminton.winzz.models.Users;
import com.badminton.winzz.repository.CustomerUserDetailsServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerUserDetailsServiceRepository customerUserDetailsServiceRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("CustomUserDetailsService called: " + username);
        return customerUserDetailsServiceRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user name not found"));
    }

    /**
     * Get the actual Users entity by username.
     * Used in JwtFilterChain to set the correct principal type
     * for @AuthenticationPrincipal Users user injection
     */
    public Users getUserByUsername(String username) throws UsernameNotFoundException {
        return (Users) customerUserDetailsServiceRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user name not found"));
    }
}
