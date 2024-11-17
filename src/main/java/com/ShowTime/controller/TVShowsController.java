package com.ShowTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ShowTime.model.MediaList;
import com.ShowTime.model.MediaListType;
import com.ShowTime.model.MediaType;
import com.ShowTime.repository.MediaListRepository;

@Controller
public class TVShowsController {

    @Autowired
    private MediaListRepository mediaListRepository;

    @GetMapping("/tvshows")
    public String moviesPage(Model model) {
        // Récupération des listes globales pour les séries
        MediaList trendingTVShows = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.TRENDING, MediaType.TV_SHOW);
        MediaList mostWatchedTVShows = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.MOST_WATCHED, MediaType.TV_SHOW);
        MediaList topRatedTVShows = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.TOP_RATED, MediaType.TV_SHOW);
        MediaList allTvShows = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.ALL, MediaType.TV_SHOW);

        model.addAttribute("allTVShows",allTvShows.getMediaList());
        model.addAttribute("trendingTVShows", trendingTVShows.getMediaList());
        model.addAttribute("mostWatchedTVShows", mostWatchedTVShows.getMediaList());
        model.addAttribute("topRatedTVShows", topRatedTVShows.getMediaList());

        return "tvshows"; // Vue pour la page des séries
    }
}
