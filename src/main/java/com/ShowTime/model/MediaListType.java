package com.ShowTime.model;

public enum MediaListType {
    // Users' Lists
    FAVORITE("Favorite"),
    TO_WATCH("To Watch"),
    WATCHED("Watched"),
    PERSONALIZED("Personalized"),

    // Global Lists (Movies/TVShows)
    ALL("All"),
    POPULAR("Popular"),
    TOP_RATED("Top Rated"),
    TRENDING("Trending"),

    // Actors' lists
    ACTOR("Actor");

    // Soit on reprend les memes, soit on crée d'autres listes pour Discover (voir idées Drive)

    private final String displayName;

    MediaListType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}