package com.ShowTime.repository;

import com.ShowTime.model.*;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;
import com.ShowTime.model.ToWatchMoviesList;

public interface ToWatchMoviesListRepository extends JpaRepository<ToWatchMoviesList, Long>{
    //List<ToWatchMoviesList> findByUser(User user);
}
