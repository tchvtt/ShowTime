package com.ShowTime.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min; 

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private User user;

    @Min(1)
    @Max(5) 
    private int rating;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    private LocalDate date;

    public Rating() {}

    public Rating(User user, Media media, int rate, String comment) {
        this.setUser(user);
        this.setMedia(media);
        this.setRating(rate);
        this.setComment(comment);
        this.date = LocalDate.now();
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    public Media getMedia() {
        return media;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getRating() {
        return rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(int rating) {
        this.rating = rating; 
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
