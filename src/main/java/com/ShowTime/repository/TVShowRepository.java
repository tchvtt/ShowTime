package com.ShowTime.repository;

import com.ShowTime.model.TVShow;
import org.springframework.data.jpa.repository.JpaRepository;

// A potentiellement delete car on peut replace par un MediaRepository (à voir)
public interface TVShowRepository extends JpaRepository<TVShow, Long> {

}
