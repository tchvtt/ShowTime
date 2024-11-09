package com.ShowTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ShowTime.model.*;
import com.ShowTime.repository.*;

import java.util.List; 

import org.springframework.ui.Model;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TVShowRepository tvShowRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private RatingRepository ratingRepository;


    @GetMapping("/{id}")
    public String showMedia(@PathVariable("id") Long id, Model model) {
        Media media = null;
        if (movieRepository.existsById(id)) {
            media = movieRepository.findById(id).orElse(null);
        } else if (tvShowRepository.existsById(id)) {
            media = tvShowRepository.findById(id).orElse(null);
        }

        if (media == null) {
            return "redirect:/"; 
        }

       //récup les acteurs associés au média
        //List<Actor> actors = actorRepository.findActorsByMediaId(id);
        //model.addAttribute("actors", actors);

        //récup les évaluations associées au média
        List<Rating> ratings = ratingRepository.findRatingsByMediaId(id);
        model.addAttribute("ratings", ratings);

        return "media-details";
    }
}
