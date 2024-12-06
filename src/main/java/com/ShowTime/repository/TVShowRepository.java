package com.ShowTime.repository;

import com.ShowTime.model.TVShow;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TVShowRepository extends JpaRepository<TVShow, Long> {

    boolean existsTVShowByTmdbID(int tmdbID);

    TVShow findTVShowByTmdbID(int tmdbID);

    List<TVShow> findByTitleContainingIgnoreCase(String title);

    // Sélectionne Un Media Au Hasard dans la base de données
    @Query(value = "SELECT id FROM media ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Long findRandomMediaId();

    List<TVShow> findByGenreIgnoreCase(String genre);
}
