package com.badminton.winzz.service;

import com.badminton.winzz.dto.SignUpDto;
import com.badminton.winzz.models.Users;
import com.badminton.winzz.repository.CustomerUserDetailsServiceRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {



    boolean status=false;

    private final CustomerUserDetailsServiceRepository customerUserDetailsServiceRepository;

    private final PasswordEncoder passwordEncoder;

    public LoginService(PasswordEncoder passwordEncoder, CustomerUserDetailsServiceRepository customerUserDetailsServiceRepository) {
        this.passwordEncoder = passwordEncoder;
        this.customerUserDetailsServiceRepository = customerUserDetailsServiceRepository;

    }

    public Users newUser(SignUpDto request){

        if (customerUserDetailsServiceRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Users user = new Users();

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());
        user.setHand(request.getHand());
        user.setMail(request.getMail());
        user.setLevel(request.getLevel());

        Users savedUser=customerUserDetailsServiceRepository.save(user);

        return savedUser;
    };

   public Optional<Users> getUser(String username){
       if(username!=null){
           return customerUserDetailsServiceRepository.findByUsername(username);
       }
return Optional.empty();
   }

}
