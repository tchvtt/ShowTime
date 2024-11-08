package com.ShowTime.controller;
import com.ShowTime.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List; 

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public String showProfile(Model model, @AuthenticationPrincipal User currentUser) {
        model.addAttribute("user", currentUser);
        return "profile";  //Vue affichant les informations de l'utilisateur
    }


    @PostMapping("/profile/update")
    public String updateUser(@AuthenticationPrincipal User currentUser, @ModelAttribute User updatedUser) {
        currentUser.setName(updatedUser.getName());
        currentUser.setAge(updatedUser.getAge());
        userRepository.save(currentUser);
        return "redirect:/profile";
    }

    @GetMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        return "redirect:/users";
    }
}
