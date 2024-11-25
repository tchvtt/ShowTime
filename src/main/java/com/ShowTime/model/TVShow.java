package com.ShowTime.model;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class TVShow extends Media {
    private Integer numberOfSeasons;
    //private final boolean isCompleted = false;
    private int tmdbID;

    public TVShow() {}

    public TVShow(int tmdbID){
        this.tmdbID = tmdbID;
    }

    public int getTmdbID(){return tmdbID;}
    /*
    //Getters
    public int getNumberOfSeasons() {return numberOfSeasons;}
    public boolean isCompleted() {return isCompleted;}
    */
    //Setters
    //public void setCompleted(boolean completed) {isCompleted = completed;}
    public void setNumberOfSeasons(Integer numberOfSeasons) {
        if (numberOfSeasons != null && numberOfSeasons > 0) {
            this.numberOfSeasons = numberOfSeasons;
        } else {
            this.numberOfSeasons = 1;
        }
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof TVShow)) return false;
        TVShow t = (TVShow) o;
        return t.getTmdbID() == this.getTmdbID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(tmdbID);  // Use tmdbID to generate the hash code
    }
}
