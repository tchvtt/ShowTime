package com.ShowTime.model;

import jakarta.persistence.*;

@Entity
public class ToWatchTVShowsList extends MediaList{
    public ToWatchTVShowsList(String name) {
        super(name);
    }
}
