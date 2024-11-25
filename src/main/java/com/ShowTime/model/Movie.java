package com.ShowTime.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class Movie extends Media{
    private Double duration;
    private int tmdbID;

    public Movie() {}

    public Movie(int tmdbID){
        this.tmdbID = tmdbID;
    }

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
    public int getTmdbID(){return tmdbID;}

    //Setters
    public void setDuration(Double duration){
        if (duration == null){
            this.duration = 0.0;
        } else {
            this.duration = duration;
        }
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Movie)) return false;
        Movie m = (Movie) o;
        return m.getTmdbID() == this.getTmdbID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(tmdbID);
    }

}
