package com.ShowTime.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List; 

@Entity
@Table(name = "app_user")       // User est réservé en H2, j'avais full erreurs et avec ça j'ai plus
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @NotNull
    @Size(min = 3, max = 20)
    @Column(unique = true)
    private String username;
   
    @NotNull
    @Email
    private String email;
    
    @NotNull
    @Size(min = 8)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MediaList> mediaLists = new ArrayList<>();

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;

        mediaLists.add(new MediaList("Watched Movies", MediaListType.WATCHED, MediaType.MOVIE, this));
        mediaLists.add(new MediaList("To Watch Movies", MediaListType.TO_WATCH, MediaType.MOVIE, this));
        mediaLists.add(new MediaList("Favorites Movies", MediaListType.FAVORITE, MediaType.MOVIE, this));
        
        mediaLists.add(new MediaList("Watched TV Shows", MediaListType.WATCHED, MediaType.TV_SHOW, this));
        mediaLists.add(new MediaList("To Watch TV Shows", MediaListType.TO_WATCH, MediaType.TV_SHOW, this));
        mediaLists.add(new MediaList("Favorites TV Shows", MediaListType.FAVORITE, MediaType.TV_SHOW, this));
    }



    // Getters et setters
    public Long getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MediaList> getMediaLists() {
        return mediaLists;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }









    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>(); 


    //gérer les ratings 
    public void addRating(Rating rating){
        this.ratings.add(rating); 
        rating.setUser(this); 
    }
    public void removeRating(Rating rating){
        this.ratings.remove(rating); 
        //dans JPA orphanRemoval = true donc censé delete le rating s'il n'est plus associé à un user 
        rating.setUser(null);
    }
}
