package com.ShowTime.model;

import jakarta.persistence.*;
@Entity
public class FavoriteTVShowsList extends MediaList{
    public FavoriteTVShowsList(String name) {
        super(name);
    }
}
