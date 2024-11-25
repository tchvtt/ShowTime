package com.ShowTime.controller;
import com.ShowTime.model.*;
import com.ShowTime.repository.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller
public class MediaDetailsController {

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TVShowRepository tvShowRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private RatingRepository ratingRepository;



    @GetMapping("/media/{id}")
    public String showMedia(@PathVariable("id") Long id, Model model) {
        // Media Infos
        Media media = null;
        if (movieRepository.existsById(id)) {
            media = movieRepository.findById(id).orElse(null);
        } else if (tvShowRepository.existsById(id)) {
            media = tvShowRepository.findById(id).orElse(null);
        }
        if (media == null) {
            return "redirect:/index"; 
        }
        model.addAttribute("media", media);

        // Media Actors
        List<Actor> actors = actorRepository.findActorsByMediaId(media.getId());
        model.addAttribute("actors", actors);


        // Media Ratings
        List<Rating> ratings = ratingRepository.findRatingsByMediaId(id);
        model.addAttribute("ratings", ratings);

        return "Media/Details";
    }




    
    /* 
    //Ajouter un média à une liste
    @PostMapping("/{id}/add-to-list")
    public String addMediaToList(@PathVariable("id") Long mediaId, @RequestParam("listType") String listType, @AuthenticationPrincipal User currentUser) {

        //Récupérer le média dans l'un des repositories
        Media media = movieRepository.findById(mediaId).orElse(null);
        if (media == null) {
            media = tvShowRepository.findById(mediaId).orElse(null);
        }
        if (media == null) {
            return "redirect:/"; //Redirection si le média n'existe pas
        }

        return "redirect:/media/" + mediaId; //Retour à la page du média
    }
    */
}
