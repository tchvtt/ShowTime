package com.ShowTime.repository;

import com.ShowTime.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsMovieByTmdbID(int tmdbID);
    Movie findMovieByTmdbID(int tmdbID);

}
