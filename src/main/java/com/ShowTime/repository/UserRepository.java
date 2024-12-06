package com.ShowTime.repository;

import com.ShowTime.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findById(Long id); 

    User findByUsername(String username); 

    boolean existsByUsername(String username);  

    boolean existsById(Long id);  

    void deleteById(Long id);
}
