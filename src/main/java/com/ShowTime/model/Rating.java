package com.ShowTime.model;

import java.time.LocalDate;

import jakarta.persistence.*; 

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Enumerated(EnumType.ORDINAL)
    private Ratings rating;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

    private LocalDate date;

    public Rating() {}

    public enum Ratings {
        ONE, TWO, THREE, FOUR, FIVE
    }

    //Getters
    public Long getId(){
        return id; 
    }
    public User getAuthor(){
        return author; 
    }
    public String getComment(){
        return comment;    
    }
    public Media getMedia(){
        return media; 
    }
    public LocalDate getDate(){
        return date; 
    }

    //Setters
    public void setComment(String comment){
        this.comment = comment; 
    }
    public void setRating(Ratings rating){
        this.rating = rating; 
    }
    public void setAuthor(User author){
        this.author = author; 
    }

}
