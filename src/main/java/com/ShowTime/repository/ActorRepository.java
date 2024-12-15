package com.ShowTime.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ShowTime.model.Actor;
import com.ShowTime.model.Media;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    
    // Recherche un acteur par son ID TMDB
    Actor findActorByTmdbID(int tmdbID);

    boolean existsActorByTmdbID(int tmdbID);

    // Retourne la liste des acteurs pour un Media donné
    @Query(value = "SELECT a.* FROM actor a " +
    "JOIN media_actor ma ON a.id = ma.actor_id " +
    "WHERE ma.media_id = :mediaId", nativeQuery = true)
    List<Actor> findActorsByMediaId(@Param("mediaId") Long mediaId);
    
    // Retourne la liste des medias pour un Actor donné
    @Query("SELECT m FROM Media m JOIN m.actors a WHERE a.id = :actorId")
    List<Media> findMediaByActorId(@Param("actorId") Long actorId);

    // Recherche un acteur par son nom
    List<Actor> findByNameContainingIgnoreCase(String name);
}