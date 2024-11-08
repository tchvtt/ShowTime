package com.ShowTime.model;

import jakarta.persistence.*;

@Entity
public class Top extends Media{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Movie topMovie;

    @OneToOne
    private TVShow topTVShow;

    public Top() {}
}
