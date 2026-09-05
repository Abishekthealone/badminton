package com.badminton.winzz.controller;


import com.badminton.winzz.dto.RegisterLogin;
import com.badminton.winzz.dto.UserProfile;
import com.badminton.winzz.models.Users;
import com.badminton.winzz.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/register")
    public String loginPlayer(@RequestBody RegisterLogin register){

   boolean status= loginService.newUser(register);

        if (status) {
            return "User registered successfully";
        }

        return "User registration failed";
    }

    /**
     * NEW - details of whoever is holding the token.
     *
     * @AuthenticationPrincipal pulls the principal straight out of the
     * SecurityContext. JwtFilterChain put a Users object there (Users implements
     * UserDetails), so Spring can hand it back already typed - no second
     * database lookup needed.
     *
     * It can still be null if the endpoint is ever made public, so we check.
     */
    @GetMapping("/me")
    public ResponseEntity<UserProfile> me(@AuthenticationPrincipal Users user) {

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(UserProfile.from(user));
    }

 @GetMapping("/welcome")
    public String login(Principal principal){

     System.out.println("WELCOME API CALLED");
     System.out.println("Principal: " + principal);
        return "HI " +principal.getName() +" login sucessfulluy";
    }

    @GetMapping("/test")
    public String test(){
        return "test2";
    }



}
