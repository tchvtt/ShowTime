package com.ShowTime.model;

public enum MediaListType {
    // Users' Lists
    FAVORITE("Favorite"),
    TO_WATCH("To Watch"),
    WATCHED("Watched"),
    PERSONALIZED("Personalized"),

    // Global Lists (Movies/TVShows)
    TRENDING("Trending"),
    MOST_WATCHED("Most Watched"),
    TOP_RATED("Top Rated");

    // Soit on reprend les memes, soit on crée d'autres listes pour Discover (voir idées Drive)

    // Actors' lists
    //ACTOR("Actor");

    private final String displayName;

    MediaListType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}