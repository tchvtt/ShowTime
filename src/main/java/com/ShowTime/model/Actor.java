package com.ShowTime.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    @NotNull
    private LocalDate birthDate;


    @ManyToMany(mappedBy = "actors",fetch = FetchType.EAGER)
    private List<Media> mediasCastedIn = new ArrayList<>();


    public Actor() {}

    public Actor(int tmdbID){
        this.tmdbID = tmdbID;
    }

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
    public Integer getTmdbID(){return tmdbID;}

    public List<Media> getMediasCastedIn(){
        return this.mediasCastedIn;
    }

    public String getPosterURL(){return posterURL;}


    //Setters
    public void setName(String name){
        this.name = name; 
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setPosterURL(String posterURL){this.posterURL = posterURL;}

    public void addMedia(Media media) {
        if (!mediasCastedIn.contains(media)) {
            mediasCastedIn.add(media);
        }
    }

    @Override
    public String toString(){
        return "Actor: "+name+" born on "+birthDate + " with ID: "+id;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }
        if (!(o instanceof Actor actor)) {
            return false;
        }
        return actor.getTmdbID() == this.getTmdbID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(tmdbID);
    }
}
