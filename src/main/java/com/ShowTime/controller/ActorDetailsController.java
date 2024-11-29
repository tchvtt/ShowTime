package com.ShowTime.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.ShowTime.model.*;
import com.ShowTime.repository.*;


@Controller
@RequestMapping("/actor")
public class ActorDetailsController {

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("/{id}")
    public String showActor(@PathVariable("id") Long id, Model model) {
        Actor actor = actorRepository.findById(id).orElse(null);
        if (actor == null) {
            return "redirect:/";  
        }

        model.addAttribute("actor", actor);
        /* Commenté car on ne peut pas utiliser les relations
        List<Media> medias = actorRepository.findMediaByActorId(id);
        model.addAttribute("medias", medias);
         */
        
        return "Actor/Details";
    }
}
