package com.ShowTime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
        /*
        List<Movie> popularMovies = movieRepository.findAll();
        List<TVShow> popularTVShows = tvShowRepository.findAll();
        
        model.addAttribute("popularMovies", popularMovies);
        model.addAttribute("popularTVShows", popularTVShows);
        System.out.println("Nombre de films trouvés : " + popularMovies.size());
        System.out.println("Nombre de séries trouvées : " + popularTVShows.size());
        */
        return "discover"; 
    }
}
