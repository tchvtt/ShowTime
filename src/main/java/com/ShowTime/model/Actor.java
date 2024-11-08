package com.ShowTime.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    private LocalDate birthDate;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>(); 

    @ManyToMany(mappedBy = "actors")
    private List<TVShow> tvShows = new ArrayList<>(); 

    public Actor() {}

    public Actor(String name, LocalDate birthDate){
        this.name = name; 
        this.birthDate = birthDate; 
    }

    //Getters
    public Long getId(){
        return id; 
    }
    public String getName(){
        return name; 
    }
    public LocalDate getBirthDate(){
        return birthDate; 
    }
    public List<Movie> getMovies(){
        return movies; 
    }
    public List<TVShow> getTVShows(){
        return tvShows; 
    }

    //Setters
    public void setName(String name){
        this.name = name; 
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    //Gérer les movies 
    public void addMovie(Movie movie) {
        this.movies.add(movie);
        movie.getActors().add(this);
    }
    public void removeMovie(Movie movie) {
        this.movies.remove(movie);
        movie.getActors().remove(this); 
    }
    public void addTVShow(TVShow tvShow) {
        this.tvShows.add(tvShow);
        tvShow.getActors().add(this);  
    }
    public void removeTVShow(TVShow tvShow) {
        this.tvShows.remove(tvShow);
        tvShow.getActors().remove(this);  
    }
}
