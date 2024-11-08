package com.ShowTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.ShowTime.model.Actor;


@RestController
@RequestMapping("/actor")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("/{id}")
    public String showActor(@PathVariable("id") Long id, Model model) {
        Actor actor = actorRepository.findById(id).orElse(null);

        if (actor == null) {
            return "redirect:/";  //si l'acteur n'existe pas redirection vers accueil
        }

        //add les informations de l'acteur au modèle
        model.addAttribute("actor", actor);
        model.addAttribute("movies", actor.getMovies());
        model.addAttribute("tvShows", actor.getTVShows());

        return "actor-details";
    }
}
