package com.ShowTime.repository;

import com.ShowTime.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

// A potentiellement delete car on peut replace par un MediaRepository (à voir)
public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsMovieByTmdbID(int tmdbID);
    Movie findMovieByTmdbID(int tmdbID);

}
