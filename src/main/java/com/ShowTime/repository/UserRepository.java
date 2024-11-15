package com.ShowTime.repository;

import com.ShowTime.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id); // Déjà fourni par JpaRepository

    Optional<User> findByUsername(String username); // Rechercher par username
}
