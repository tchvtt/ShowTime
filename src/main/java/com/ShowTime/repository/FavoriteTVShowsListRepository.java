package com.ShowTime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ShowTime.model.FavoriteTVShowsList;

public interface FavoriteTVShowsListRepository extends JpaRepository<FavoriteTVShowsList, Long>{
    //List<FavoriteTVShowsList> findByUser(User user);
}
