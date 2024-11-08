package com.ShowTime.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List; 

import jakarta.persistence.*; 


@Entity
public abstract class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private LocalDate releaseDate;

    @ManyToMany
    //à faire JoinTable
    private List<Actor> actors = new ArrayList<>();

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    public Media() {}

    public Media(String title){
        this.title = title; 
    }

    //Getters
    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getGenre() {
        return genre;
    }
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public List<Actor> getActors() {
        return actors;
    }

    //Setters
    public void setTitle(String title) {
        this.title = title;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
