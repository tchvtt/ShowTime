package com.ShowTime.repository;

import com.ShowTime.model.*;
import java.util.List; 
import org.springframework.data.jpa.repository.JpaRepository;
import com.ShowTime.model.ToWatchTVShowsList;

public interface ToWatchTVShowsListRepository extends JpaRepository<ToWatchTVShowsList, Long>{
    List<ToWatchTVShowsList> findByUser(User user);
}
