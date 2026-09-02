package com.badminton.winzz.service;

import com.badminton.winzz.dto.RegisterLogin;
import com.badminton.winzz.models.Users;
import com.badminton.winzz.repository.CustomerUserDetailsServiceRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    boolean status=false;

    private final CustomerUserDetailsServiceRepository customerUserDetailsServiceRepository;

    private final PasswordEncoder passwordEncoder;

    LoginService(CustomerUserDetailsServiceRepository cus, PasswordEncoder passwordEncoder){
        this.customerUserDetailsServiceRepository=cus;
        this.passwordEncoder=passwordEncoder;
    }


    public boolean newUser(RegisterLogin request){

        if (customerUserDetailsServiceRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Users user = new Users();

        user.setUsername(request.getUsername());


        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole("USER");
        user.setHand(request.getHand());
        user.setMail(request.getMail());
        user.setLevel(request.getLevel());

        customerUserDetailsServiceRepository.save(user);

        return true;
    };
}
