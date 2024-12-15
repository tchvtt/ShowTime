package com.ShowTime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ShowTime.model.Rating;
import com.ShowTime.model.User;
import com.ShowTime.repository.RatingRepository;
import com.ShowTime.repository.UserRepository;
import com.ShowTime.security.CustomUserDetails;
@Controller
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Affiche la page de profil
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

    // Modifier les informations du profil
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

    // Supprimer compte
    @PostMapping("/profile/delete")
    public String deleteAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        Long userId = customUserDetails.getUser().getId();
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
        SecurityContextHolder.clearContext();      

        return "redirect:/logout";
    }

}
