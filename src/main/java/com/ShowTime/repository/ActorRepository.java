package com.ShowTime.repository;

import com.ShowTime.model.Actor;
import com.ShowTime.model.Media;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Actor findActorByTmdbID(int tmdbID);

    boolean existsActorByTmdbID(int tmdbID);

    @Query(value = "SELECT a.* FROM actor a " +
    "JOIN media_actor ma ON a.id = ma.actor_id " +
    "WHERE ma.media_id = :mediaId", nativeQuery = true)
    List<Actor> findActorsByMediaId(@Param("mediaId") Long mediaId);
    
    
    @Query("SELECT m FROM Media m JOIN m.actors a WHERE a.id = :actorId")
    List<Media> findMediaByActorId(@Param("actorId") Long actorId);
}