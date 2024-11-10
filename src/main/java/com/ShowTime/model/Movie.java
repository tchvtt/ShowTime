package com.ShowTime.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Movie extends Media{
    private double duration;

    public Movie() {}

    public Movie(String title, String genre, LocalDate releaseDate, double duration) {
        super(title); 
        this.setGenre(genre);
        this.setReleaseDate(releaseDate);
        this.duration = duration; 
    }

    //Getters 
    public double getDuration(){
        return duration; 
    }

    //Setters
    public void setDuration(double duration){
        this.duration = duration; 
    }

    //Gérer les acteurs liés au film 
    public void addActor(Actor actor) {
        getActors().add(actor);
        actor.getMovies().add(this);
    }
    public void removeActor(Actor actor) {
        getActors().remove(actor);
        actor.getMovies().remove(this);
    }
}
