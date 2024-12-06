package com.ShowTime.controller;

import com.ShowTime.model.*;
import com.ShowTime.repository.*;
import com.ShowTime.security.CustomUserDetails;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
@Controller
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

    @PostMapping("/profile/update")
    public String updateUserProfile(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @RequestParam("email") String email,
        @RequestParam("password") String password,  Model model, RedirectAttributes redirectAttributes) {

            System.out.println("Email: " + email);
            System.out.println("Password: " + password);    

        if (customUserDetails == null) {
            return "redirect:/login";
        }

        if (email.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email cannot be empty");
            return "redirect:/profile";
        }
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            redirectAttributes.addFlashAttribute("error", "Invalid email format");
            return "redirect:/profile";
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

    @PostMapping("/profile/delete")
    public String deleteAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        Long userId = customUserDetails.getUser().getId();
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
        SecurityContextHolder.clearContext();

        /*
        for (long i = 1; i <= 14; i++) {
            userRepository.deleteById(i);
        }
        */        

        return "redirect:/logout";
    }


    /*
    // See others profiles
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
    */

}
