package com.ShowTime.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class AverageRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double averageRating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id", nullable = false)
    private Media media;

    // Calcul de la moyenne des évaluations et mise à jour
    public void calculateAndSetAverageRating(List<Integer> ratings) {
        if (ratings == null || ratings.isEmpty()) {
            this.averageRating = 0.0; // Par défaut si aucune évaluation
        } else {
            this.averageRating = ratings.stream()
                                        .mapToDouble(Integer::doubleValue)
                                        .average()
                                        .orElse(0.0);
        }
    }



    public Double getAverageRating() {
        return averageRating;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
