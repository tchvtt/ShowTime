package com.ShowTime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ShowTime.model.FavoriteMoviesList;

public interface FavoriteMoviesListRepository extends JpaRepository<FavoriteMoviesList, Long>{
    //List<FavoriteMoviesList> findByUser(User user);
}
