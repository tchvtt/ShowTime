package com.ShowTime.repository;

import com.ShowTime.model.TVShow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TVShowRepository extends JpaRepository<TVShow, Long> {

    boolean existsTVShowByTmdbID(int tmdbID);
    TVShow findTVShowByTmdbID(int tmdbID);

    List<TVShow> findByTitleContainingIgnoreCase(String title);

}
