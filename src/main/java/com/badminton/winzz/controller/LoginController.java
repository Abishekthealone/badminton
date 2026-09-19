package com.badminton.winzz.controller;


import com.badminton.winzz.dto.PlayerResponseDto;
import com.badminton.winzz.dto.SignUpDto;
import com.badminton.winzz.dto.UserProfile;
import com.badminton.winzz.models.Users;
import com.badminton.winzz.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLOutput;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/sign_up")
    public ResponseEntity<Users> signUp(@RequestBody SignUpDto register) {
        Users user = loginService.newUser(register);
        System.out.println("userData "+user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfile> me(@AuthenticationPrincipal Users user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(UserProfile.from(user));
    }


    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<PlayerResponseDto> getUser(@PathVariable String username) {
        PlayerResponseDto dto = new PlayerResponseDto();
        if (username != null) {
            Optional<Users> user = loginService.getUser(username);


            if (user.isPresent()) {
                dto.setUsername(user.get().getUsername());
                dto.setFirstName(user.get().getFirstName());
                dto.setLastName(user.get().getMail());
                dto.setLevel(user.get().getLevel());
                dto.setMail(user.get().getMail());
                dto.setHand(user.get().getHand());


            }
        }
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public String login(Principal principal) {

        System.out.println("WELCOME API CALLED");
        System.out.println("Principal: " + principal);
        return "HI " + principal.getName() + " login sucessfulluy";
    }

    @GetMapping("/test")
    public String test() {
        return "test2";
    }
}
