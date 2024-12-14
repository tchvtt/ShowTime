package com.ShowTime.repository;

import com.ShowTime.model.AverageRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AverageRatingRepository extends JpaRepository<AverageRating, Long> {
    
}
