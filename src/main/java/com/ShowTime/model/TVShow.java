package com.ShowTime.model;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
public class TVShow extends Media {
    private Integer numberOfSeasons;
    private Boolean isCompleted;
    private int tmdbID;

    public TVShow() {}

    public TVShow(int tmdbID){
        this.tmdbID = tmdbID;
    }

    public int getTmdbID(){return tmdbID;}

    //Getters
    public int getNumberOfSeasons() {return numberOfSeasons;}
    public boolean isCompleted() {return isCompleted;}

    //Setters
    public void setCompleted(Boolean completed) {
        if (completed != null) {
            this.isCompleted = completed;
        } else {
            this.isCompleted = true;
        }
    }
    public void setNumberOfSeasons(Integer numberOfSeasons) {
        if (numberOfSeasons != null && numberOfSeasons > 0) {
            this.numberOfSeasons = numberOfSeasons;
        } else {
            this.numberOfSeasons = 1;
        }
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

    @Override
    public String toString() {
        return ("TV Show :" + this.getTitle() + "\nGenre :" + this.getGenre() + "\nRelease date :"
                + this.getReleaseDate() + "\nNumber of seasons :" + this.getNumberOfSeasons() + "\nCompleted ? "
                + this.isCompleted() + "\nActors :" + this.getActors()
                + "\n\nSummary :" + this.getOverview());
    }
}
