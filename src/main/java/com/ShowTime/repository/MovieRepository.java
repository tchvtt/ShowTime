package com.ShowTime.repository;

import com.ShowTime.model.Movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsMovieByTmdbID(int tmdbID);
    Movie findMovieByTmdbID(int tmdbID);

    List<Movie> findByTitleContainingIgnoreCase(String title);

    @Query(value = "SELECT id FROM media ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Long findRandomMediaId();

}
