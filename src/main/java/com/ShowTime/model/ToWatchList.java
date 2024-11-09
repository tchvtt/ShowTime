package com.ShowTime.model;

import jakarta.persistence.*;

@Entity
public class ToWatchList extends MediaList{
    public ToWatchList(String name) {
        super(name);
    }
}
