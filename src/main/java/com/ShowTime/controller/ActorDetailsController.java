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

    // Afficher la page d'un Actor
    @GetMapping("/{id}")
    public String showActor(@PathVariable("id") Long id, Model model) {
        Actor actor = actorRepository.findById(id).orElse(null);
        if (actor == null) {
            return "redirect:/";  
        }

        model.addAttribute("actor", actor);
        
        return "Actor/Details";
    }
}
