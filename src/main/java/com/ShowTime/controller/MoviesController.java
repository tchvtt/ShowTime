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
        MediaList allMovies = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.ALL, MediaType.MOVIE);
        MediaList popularMovies = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.POPULAR, MediaType.MOVIE);
        MediaList topRatedMovies = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.TOP_RATED, MediaType.MOVIE);
        MediaList trendingMovies = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.TRENDING, MediaType.MOVIE);

        model.addAttribute("allMovies", allMovies.getMediaList());
        model.addAttribute("popularMovies", popularMovies.getMediaList());
        model.addAttribute("topRatedMovies", topRatedMovies.getMediaList());
        model.addAttribute("trendingMovies", trendingMovies.getMediaList());

        return "movies";
    }
}
