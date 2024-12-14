package com.ShowTime.controller;

import com.ShowTime.model.User;
import com.ShowTime.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    /*
    @Autowired
    private PasswordEncoder passwordEncoder;
    */

    // Affiche la page de connexion
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, 
                                 @RequestParam(value = "logout", required = false) String logout, 
                                 @RequestParam(value = "registrationSuccess", required = false) String registrationSuccess,
                                 @RequestParam(value = "accountDeleted", required = false) String accountDeleted,
                                 Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been successfully logged out.");
            model.addAttribute("registrationSuccess", false);
        }
        if (registrationSuccess != null && registrationSuccess.equals("true")) {
            model.addAttribute("registrationSuccess", true);
        }
        
        return "login"; 
    }

    // Affiche la page d'inscription
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User()); 
        return "register"; 
    }

    // Gère l'inscription des utilisateurs
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User userForm, RedirectAttributes redirectAttributes) {
        // Vérifie si le username est déjà pris
        if (userRepository.existsByUsername(userForm.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username is already taken.");
            return "redirect:/register";
        }

        // Vérifie si l'email est au bon format
        if (userForm.getEmail() == null || 
            !userForm.getEmail().matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            redirectAttributes.addFlashAttribute("error", "Invalid email format.");
            return "redirect:/register";
        }

        // Crypte le mot de passe
        //String encodedPassword = passwordEncoder.encode(userForm.getPassword());
        User user = new User(userForm.getUsername(), userForm.getEmail(), userForm.getPassword());

        // Sauvegarde l'utilisateur
        userRepository.save(user);

        // Redirige vers la page de connexion
        redirectAttributes.addFlashAttribute("registrationSuccess", "Account created successfully. You can now log in.");
        return "redirect:/login";
    }
}
