package com.badminton.winzz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Serves the temporary Thymeleaf pages.
 *
 * NOTE: this is @Controller, not @RestController. @RestController would write
 * the returned String straight into the response body; @Controller treats it as
 * a view name, which Thymeleaf resolves to
 * src/main/resources/templates/<name>.html
 *
 * Every page here is an empty HTML shell. None of them carry data - the data is
 * fetched by JavaScript from the JSON API using the JWT. When React replaces
 * this UI, delete this controller and the templates folder; no other backend
 * change is needed.
 */
@Controller
@RequestMapping("/ui")
public class WebPageController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    /** Reads ?tournamentId= from the query string in JavaScript. */
    @GetMapping("/scoreboard")
    public String scoreboard() {
        return "scoreboard";
    }

    /** Reads ?id= (single player) or ?tournamentId= (roster). */
    @GetMapping("/player")
    public String player() {
        return "player";
    }

    @GetMapping("/tournaments")
    public String tournaments() {
        return "tournaments";
    }
}
