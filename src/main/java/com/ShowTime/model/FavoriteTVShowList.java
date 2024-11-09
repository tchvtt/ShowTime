package com.ShowTime.model;

import jakarta.persistence.*;
@Entity
public class FavoriteTVShowList extends MediaList{
    public FavoriteTVShowList(String name) {
        super(name);
    }
}
