package com.ShowTime.model;

import jakarta.persistence.*;

@Entity
public class FavoriteMoviesList extends MediaList{
    public FavoriteMoviesList(String name) {
        super(name);
    }
}
