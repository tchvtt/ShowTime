package com.ShowTime.controller;
import com.ShowTime.model.*;
import com.ShowTime.repository.UserRepository;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Affichage de la page de connexion
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }

    //Affichage de la page d'inscription
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register"; 
    }

    //Traitement de la soumission du formulaire d'inscription
    /* 
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        //Encoder le mot de passe avant de sauvegarder l'utilisateur
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user); 
        return "redirect:/login"; 
    }
    */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User userForm, RedirectAttributes redirectAttributes) {

        //check si username pas déjà pris 
        if (userRepository.existsByUsername(userForm.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username is already taken");
            return "redirect:/register";
        }

        //check si email est dans le bon format 
        if (userForm.getEmail() == null || !userForm.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            redirectAttributes.addFlashAttribute("error", "Invalid email format");
            return "redirect:/register";
        }

        String encodedPassword = passwordEncoder.encode(userForm.getPassword());
        User user = new User(userForm.getUsername(), userForm.getEmail(), encodedPassword);
        userRepository.save(user);
        return "redirect:/login"; 
    }
}
