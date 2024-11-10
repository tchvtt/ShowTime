package com.ShowTime.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import com.ShowTime.model.ToWatchMoviesList;

public interface ToWatchMoviesListRepository extends JpaRepository<ToWatchMoviesList, Long>{
    //List<ToWatchMoviesList> findByUser(User user);
}
