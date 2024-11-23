package com.ShowTime.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import jakarta.persistence.*;


@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private LocalDate releaseDate;

    @Column(length = 10000)
    private String overview;

    private String posterURL;

    @ManyToMany
    @JoinTable(
        name = "media_actor",
        joinColumns = @JoinColumn(name = "media_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private LinkedHashSet<Actor> actors = new LinkedHashSet<>();

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
    public LinkedHashSet<Actor> getActors() {
        return actors;
    }
    public String getOverview(){
        return overview;
    }
    public String getPosterURL(){
        return posterURL;
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
    public void setOverview(String overview){
        this.overview = overview;
    }
    public void setPosterURL(String posterURL){
        this.posterURL = posterURL;
    }
    public void setActors(LinkedHashSet<Actor> actors) {this.actors = actors;}

    //Gérer les medias associés
    public void addActor(Actor actor) {
        getActors().add(actor);
        //actor.getMediaList().getMediaList().add(this);
        actor.getMediaList().add(this);

    }
    public void removeActor(Actor actor) {
        getActors().remove(actor);
        //actor.getMediaList().getMediaList().remove(this);
        actor.getMediaList().remove(this);
    }
}
