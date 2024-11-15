package com.ShowTime.controller;

import com.ShowTime.model.*;
import com.ShowTime.repository.*;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/user")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElse(null);

        if (user == null) {
            return "error/404";
        }

        model.addAttribute("user", user);
        return "User/Profile";
    }
















    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "user/profile";  //Vue du profil de l'user
    }


    @PostMapping("/profile/update")
    public String updateUser(@AuthenticationPrincipal User currentUser, @Valid @ModelAttribute User updatedUser, BindingResult result) {
        if (result.hasErrors()) {
            //gère avec thymelead l'affichage des erreurs 
            return "profile/update"; 
        }
        currentUser.setUsername(updatedUser.getUsername());
        currentUser.setEmail(updatedUser.getEmail());
        userRepository.save(currentUser);
        return "redirect:/user/profile";
    }


    @PostMapping("/profile/delete")
    //à faire : gérer le fait que quand l'user delete son profil il est auto déconnecté 
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        //redirect page d'accueil
        return "redirect:/";
    }


    @GetMapping("/profile/media-lists")
    public String showAllMediaLists(Model model, @AuthenticationPrincipal User currentUser) {
        /*
        model.addAttribute("favoriteMovies", favoriteMoviesListRepository.findByUser(currentUser));
        model.addAttribute("favoriteTVShows", favoriteTVShowsListRepository.findByUser(currentUser));
        model.addAttribute("toWatchMovies", toWatchMoviesListRepository.findByUser(currentUser));
        model.addAttribute("toWatchTVShows", toWatchTVShowsListRepository.findByUser(currentUser));
        */
        return "user-media-lists";
    }
}