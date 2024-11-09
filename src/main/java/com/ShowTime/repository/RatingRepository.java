package com.ShowTime.repository;

import com.ShowTime.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    // Recherche des évaluations associées à un média spécifique
    List<Rating> findRatingsByMediaId(Long mediaId);
    
    // Recherche des évaluations d'un utilisateur spécifique
    List<Rating> findRatingsByAuthorId(Long userId);
}
