package com.ShowTime.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ShowTime.model.Movie;
import com.ShowTime.model.TVShow;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.TVShowRepository;

@Controller
public class DiscoverController {
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TVShowRepository tvShowRepository;

    @GetMapping("/discover")
    public String showIndex(Model model) {

        return "discover"; 
    }

    @GetMapping("/surprise-me")
    public String surpriseMe() {
        try {
            Long randomId = movieRepository.findRandomMediaId();
            return "redirect:/media/" + randomId;
        } catch (Exception e) {
            return "redirect:/discover"; 
        }
    }
}
