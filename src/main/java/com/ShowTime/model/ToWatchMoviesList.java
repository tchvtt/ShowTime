package com.ShowTime.model;

import jakarta.persistence.*;

@Entity
public class ToWatchMoviesList extends MediaList{
    public ToWatchMoviesList(String name) {
        super(name);
    }
}
