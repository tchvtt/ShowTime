package com.ShowTime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/discover")
    public String discover() {
        return "discover";
    }

    @GetMapping("/movies")
    public String movies() {
        return "movies";
    }

    @GetMapping("/tvshows")
    public String tvShows() {
        return "tvshows";
    }

    @GetMapping("/account")
    public String account() {
        return "account";
    }
}
