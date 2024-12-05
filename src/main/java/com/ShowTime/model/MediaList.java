package com.ShowTime.model;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class MediaList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private User user;  // Peut être null pour des listes générales

    private MediaListType mediaListType;

    private MediaType mediaType;

    @ManyToMany
    @JoinTable(
        name = "media_list_media",
        joinColumns = @JoinColumn(name = "media_list_id"),
        inverseJoinColumns = @JoinColumn(name = "media_id")
     )
    private Set<Media> mediaList = new LinkedHashSet<>();



    public MediaList() {}

    // Global
    public MediaList(String name, MediaListType mediaListType, MediaType mediaType) {
        this.name = name;
        this.date = LocalDate.now();
        this.mediaListType = mediaListType;
        this.mediaType = mediaType;
    }

    // User
    public MediaList(String name,  MediaListType mediaListType, MediaType mediaType, User user) {
        this(name, mediaListType, mediaType);
        this.user = user;
    }



    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    public MediaListType getMediaListType() {
        return mediaListType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public Set<Media> getMediaList() {
        return mediaList;
    }



    // Setters 
    public void setName(String name) {
        this.name = name;
    }



    // Gestion de la liste
    public void addMedia(Media media) {
        mediaList.add(media);
    }
    public void removeMedia(Media media) {
        mediaList.remove(media);
    }
    public boolean containsMedia(Media media) {
        return mediaList.contains(media);
    }
}
