package com.badminton.winzz.service;

import com.badminton.winzz.dto.RegisterLogin;
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
        return customerUserDetailsServiceRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user name not found"));
    }





    }