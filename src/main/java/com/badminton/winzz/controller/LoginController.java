package com.badminton.winzz.controller;


import com.badminton.winzz.dto.RegisterLogin;
import com.badminton.winzz.models.Users;
import com.badminton.winzz.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
