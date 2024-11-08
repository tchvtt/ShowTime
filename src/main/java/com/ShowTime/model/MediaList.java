package com.ShowTime.model;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List; 

import jakarta.persistence.*;

@Entity
public abstract class MediaList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate date;

    @ManyToMany
    private List<Media> mediaItems = new ArrayList<>();

    public MediaList(String name) {
        this.name = name;
        this.date = LocalDate.now();
    }

    //Getters
    public Long getId(){
        return id; 
    }
    public String getName(){
        return name; 
    }
    public LocalDate getDate(){
        return date; 
    }
    public List<Media> getMediaItems(){
        return mediaItems; 
    }

    //Setters 
    public void setName(String name){
        this.name = name; 
    }

    //Gérer les médias qu'on add ou remove des listes 
    public void addMedia(Media media) {
        mediaItems.add(media);
    }
    public void removeMedia(Media media) {
        mediaItems.remove(media);
    }
    public boolean containsMedia(Media media) {
        return mediaItems.contains(media);
    }
}
