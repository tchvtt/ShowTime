package com.ShowTime.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int tmdbID;
    private String posterURL;

    @NotNull
    private String name;
    //private String biography;
    @NotNull
    private LocalDate birthDate;
    //private String profileImage;


    @ManyToMany(mappedBy = "actors")
    private List<Movie> MoviesCastedIn = new ArrayList<>();


    @ManyToMany
    private List<TVShow> TVShowsCastedIn = new ArrayList<>();


    /*
    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies = new ArrayList<>(); 

    @ManyToMany(mappedBy = "actors")
    private List<TVShow> tvShows = new ArrayList<>(); 
    */

    public Actor() {}

    public Actor(int tmdbID){
        this.tmdbID = tmdbID;
    }

    public Actor(String name, LocalDate birthDate){
        this.name = name; 
        this.birthDate = birthDate;
        //this.mediaList = new MediaList(name, MediaListType.ACTOR, MediaType.ANY);
        //this.mediaList = new LinkedHashSet<>();

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
    public Integer getTmdbID(){return tmdbID;}

    public List<Movie> getMoviesCastedIn(){
        return this.MoviesCastedIn;
    }

    public List<TVShow> getTVShowsCastedIn(){
        return this.TVShowsCastedIn;
    }

    /* 
    public List<Movie> getMovies(){
        return movies; 
    }
    public List<TVShow> getTVShows(){
        return tvShows; 
    }
    */

    //Setters
    public void setName(String name){
        this.name = name; 
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setPosterURL(String posterURL){this.posterURL = posterURL;}

    public void addMovie(Movie movie){
        this.MoviesCastedIn.add(movie);
    }
    /*
    // Gérer les médias
    public void addMedia(Media media) {
        if (!mediaList.getMediaList().contains(media)) {
            mediaList.getMediaList().add(media);
            media.getActors().add(this);
        }
    }

    public void removeMedia(Media media) {
        mediaList.getMediaList().remove(media);
        media.getActors().remove(this);
    }



    // Gérer les médias
    public void addMedia(Media media) {
        if (!mediaList.contains(media)) {
            mediaList.add(media);
            media.getActors().add(this);
        }
    }

    public void removeMedia(Media media) {
        mediaList.remove(media);
        media.getActors().remove(this);
    }
    */

    //Gérer les movies 
    /*
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
    */
    public String toString(){
        return "Actor: "+name+" born on "+birthDate + " with ID: "+id;
    }
}
