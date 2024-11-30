package com.ShowTime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import com.ShowTime.model.Actor;
import com.ShowTime.model.Movie;
import com.ShowTime.model.TVShow;
import com.ShowTime.repository.ActorRepository;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.TVShowRepository;

@Controller
public class ResultsController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TVShowRepository tvShowRepository;

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("search/results")
    public String searchResults(@RequestParam("q") String query, Model model) {
        List<Movie> movies = movieRepository.findByTitleContainingIgnoreCase(query);
        List<TVShow> tvShows = tvShowRepository.findByTitleContainingIgnoreCase(query);
        List<Actor> actors = actorRepository.findByNameContainingIgnoreCase(query);

        model.addAttribute("movies", movies);
        model.addAttribute("tvShows", tvShows);
        model.addAttribute("actors", actors);
        model.addAttribute("query", query);

        return "searchresults";  // Le nom de la vue Thymeleaf
    }
}
