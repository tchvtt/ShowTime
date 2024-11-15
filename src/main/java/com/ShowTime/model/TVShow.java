package com.ShowTime.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class TVShow extends Media {
    private int numberOfSeasons;
    private boolean isCompleted;

    public TVShow() {}

    public TVShow(String title, String genre, LocalDate releaseDate, int numberOfSeasons, boolean isCompleted) {
        super(title); 
        this.setGenre(genre);
        this.setReleaseDate(releaseDate);
        this.numberOfSeasons = numberOfSeasons;
        this.isCompleted = isCompleted;
    }

    //Getters
    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }
    public boolean isCompleted() {
        return isCompleted;
    }
    
    //Setters
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    //Gérer les acteurs liés à la série
    public void addActor(Actor actor) {
        getActors().add(actor);
        //actor.getTVShows().add(this);
    }
    public void removeActor(Actor actor) {
        getActors().remove(actor);
        //actor.getTVShows().remove(this);
    }
}
