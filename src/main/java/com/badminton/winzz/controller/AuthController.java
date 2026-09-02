package com.badminton.winzz.controller;

import com.badminton.winzz.dto.RegisterLogin;
import com.badminton.winzz.util.JWTutil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JWTutil jwTutil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/token")
    public String auth(@RequestBody RegisterLogin request){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        }
        catch(Exception e){
            throw e;
        }




        return jwTutil.generateJwt(request.getUsername());




    }

}
