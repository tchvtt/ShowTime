package com.ShowTime.repository;

import com.ShowTime.model.Actor;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {

    //List<Actor> findActorsByMediaId(Long id);
    
}
