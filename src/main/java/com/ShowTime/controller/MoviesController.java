package com.ShowTime.controller;

import com.ShowTime.repository.MediaListRepository;
import com.ShowTime.model.MediaList;
import com.ShowTime.model.MediaListType;
import com.ShowTime.model.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MoviesController {

    @Autowired
    private MediaListRepository mediaListRepository;

    @GetMapping("/movies")
    public String moviesPage(Model model) {
        // Récupération des listes globales pour les films
        MediaList trendingMovies = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.TRENDING, MediaType.MOVIE);
        MediaList mostWatchedMovies = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.MOST_WATCHED, MediaType.MOVIE);
        MediaList topRatedMovies = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.TOP_RATED, MediaType.MOVIE);
        System.out.println(trendingMovies);

        model.addAttribute("trendingMovies", trendingMovies.getMediaList());
        model.addAttribute("mostWatchedMovies", mostWatchedMovies.getMediaList());
        model.addAttribute("topRatedMovies", topRatedMovies.getMediaList());

        return "movies"; // Vue pour la page des films
    }
}
