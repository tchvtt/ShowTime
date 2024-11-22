package com.ShowTime.controller;

import com.ShowTime.model.*;
import com.ShowTime.repository.*;
import com.ShowTime.security.CustomUserDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
@Controller
//@RequestMapping("/user")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String showLoggedInUserProfile(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        if (customUserDetails == null) {
            return "redirect:/login"; 
        }

        User user = userRepository.findById(customUserDetails.getUser().getId()).orElse(null);
        if (user == null) {
            return "redirect:/"; 
        }
        model.addAttribute("user", user);

        List<Rating> ratings = ratingRepository.findRatingsByUserId(user.getId());
        model.addAttribute("ratings", ratings);

        return "User/Profile"; 
    }

    @GetMapping("/user/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute("user", user);

        List<Rating> ratings = ratingRepository.findRatingsByUserId(id);
        model.addAttribute("ratings", ratings);

        return "User/Profile";
    }

    @PostMapping("/profile/update")
    public String updateUserProfile(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam("email") String email,
        @RequestParam("password") String password,  Model model) {

            System.out.println("Email: " + email);
            System.out.println("Password: " + password);    

        if (customUserDetails == null) {
            return "redirect:/login";
        }

        if (email.isEmpty() || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            model.addAttribute("error", "Invalid email format");
            return "User/Profile";
        }
        

        User user = userRepository.findById(customUserDetails.getUser().getId()).orElse(null);
        if (user == null) {
            return "redirect:/";
        }

        user.setEmail(email);
        if (!password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password)); 
        }
        userRepository.save(user);

        return "redirect:/profile";
    }



    /*
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
    */
}
