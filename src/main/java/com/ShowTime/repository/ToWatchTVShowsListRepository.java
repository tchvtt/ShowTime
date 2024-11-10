package com.ShowTime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ShowTime.model.ToWatchTVShowsList;

public interface ToWatchTVShowsListRepository extends JpaRepository<ToWatchTVShowsList, Long>{
    //List<ToWatchTVShowsList> findByUser(User user);
}
