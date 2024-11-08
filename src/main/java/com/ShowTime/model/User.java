package com.ShowTime.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List; 

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>(); 

    @OneToOne(cascade = CascadeType.ALL)
    private FavoriteMoviesList favoriteMoviesList; 

    @OneToOne(cascade = CascadeType.ALL)
    private FavoriteTVShowList favoriteTVShowList;

    @OneToOne(cascade = CascadeType.ALL)
    private ToWatchList moviesToWatchList;

    @OneToOne(cascade = CascadeType.ALL)
    private ToWatchList tvShowsToWatchList;

    public User() {
        this.favoriteMoviesList = new FavoriteMoviesList("Favorite Movies");
        this.favoriteTVShowList = new FavoriteTVShowList("Favorite TV Shows");
        this.moviesToWatchList = new ToWatchList("Movies To Watch List");
        this.tvShowsToWatchList = new ToWatchList("TVShows To Watch List");
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.favoriteMoviesList = new FavoriteMoviesList("Favorite Movies");
        this.favoriteTVShowList = new FavoriteTVShowList("Favorite TV Shows");
        this.moviesToWatchList = new ToWatchList("Movies To Watch List");
        this.tvShowsToWatchList = new ToWatchList("TVShows To Watch List");
    }

    //Getters
    public Long getId(){
        return this.id; 
    }
    public String getName(){
        return this.name; 
    }
    public String getEmail(){
        return this.email; 
    }
    //?
    public String getPassword(){
        return this.password; 
    }
    
    //Setters
    public void setName(String name){
        this.name = name; 
    }
    public void setEmail(String email){
        this.email = email; 
    }
    public void setPassword(String password){
        this.password = password; 
    }


    //gérer les médias dans les listes de base 
    public void addFavoriteMovie(Movie favoriteMovie) {
        this.favoriteMoviesList.addMedia(favoriteMovie); 
    }
    public void removeFavoriteMovie(Movie favoriteMovie) {
        this.favoriteMoviesList.removeMedia(favoriteMovie);
    }

    public void addFavoriteTVShow(TVShow favoriteTVShow) {
        this.favoriteTVShowList.addMedia(favoriteTVShow); 
    }
    public void removeFavoriteTVShow(TVShow favoriteTVShow) {
        this.favoriteTVShowList.removeMedia(favoriteTVShow); 
    }

    public void addMoviesToWatch(Media media) {
        this.moviesToWatchList.addMedia(media); 
    }
    public void removeMoviesToWatch(Media media) {
        this.moviesToWatchList.removeMedia(media); 
    }

    public void addTVShowsToWatch(Media media) {
        this.tvShowsToWatchList.addMedia(media); 
    }
    public void removeTVShowsToWatch(Media media) {
        this.tvShowsToWatchList.removeMedia(media); 
    }

    
    //gérer les ratings 
    public void addRating(Rating rating){
        this.ratings.add(rating); 
        rating.setAuthor(this); 
    }
    public void removeRating(Rating rating){
        this.ratings.remove(rating); 
        //dans JPA orphanRemoval = true donc censé delete le rating s'il n'est plus associé à un user 
        rating.setAuthor(null);
    }
}
