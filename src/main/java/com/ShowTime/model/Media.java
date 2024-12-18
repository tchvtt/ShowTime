package com.ShowTime.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
public abstract class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private LocalDate releaseDate;

    @Column(length=1000)
    @ElementCollection
    private List<Integer> actorsID = new ArrayList<>();

    @Column(length = 10000)
    private String overview;

    private String posterURL;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "media_actor",
        joinColumns = @JoinColumn(name = "media_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "media")
    private AverageRating averageRating;

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
    public List<Actor> getActors() {
        return actors;
    }
    public String getOverview(){
        return overview;
    }
    public String getPosterURL(){
        return posterURL;
    }
    public List<Integer> getActorsID() {
        return actorsID;
    }
    public AverageRating getAverageRating() {
        return averageRating;
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
    public void setActors(List<Actor> actors) {this.actors = actors;}

    public void setActorsID(List<Integer> actorsID) {
        this.actorsID = actorsID;
    }
    public void setAverageRating(AverageRating averageRating) {
        this.averageRating = averageRating;
    }

    //Gérer les medias associés
    public void addActor(Actor actor) {
        if (!actors.contains(actor)) {
            actors.add(actor);
        }
    }
    
    public void removeActor(Actor actor) {
        getActors().remove(actor);
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Media)) return false;
        Media m = (Media) o;
        return m.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
