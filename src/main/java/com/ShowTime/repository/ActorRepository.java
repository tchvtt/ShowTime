package com.ShowTime.repository;

import com.ShowTime.model.Actor;
import com.ShowTime.model.Media;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    //List<Actor> findByMediaListContains(Media media);
    //List<Actor> findByMediaId(Long media_id);

    // Surement possible sans Query mais legit je comprends pas j'ai tout essayé avec les relations, là au moins ca marche
    @Query(value = "SELECT a.* FROM actor a " +
    "JOIN media_actor ma ON a.id = ma.actor_id " +
    "WHERE ma.media_id = :mediaId", nativeQuery = true)
    List<Actor> findActorsByMediaId(@Param("mediaId") Long mediaId);
    
    
    @Query("SELECT m FROM Media m JOIN m.actors a WHERE a.id = :actorId")
    List<Media> findMediaByActorId(@Param("actorId") Long actorId);

}