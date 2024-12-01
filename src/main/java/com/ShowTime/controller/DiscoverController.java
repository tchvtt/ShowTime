package com.ShowTime.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ShowTime.model.MediaList;
import com.ShowTime.model.MediaListType;
import com.ShowTime.model.MediaType;
import com.ShowTime.model.Movie;
import com.ShowTime.model.TVShow;
import com.ShowTime.repository.MediaListRepository;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.TVShowRepository;

@Controller
public class DiscoverController {
    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TVShowRepository tvShowRepository;

    @Autowired
    private MediaListRepository mediaListRepository;

    @GetMapping("/discover")
    public String showIndex(Model model) {

        MediaList featured = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.FEATURED, MediaType.ANY);
        MediaList recommended = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.RECOMMENDED, MediaType.ANY);
        MediaList newReleases = mediaListRepository.findByMediaListTypeAndMediaType(MediaListType.NEW_RELEASES, MediaType.ANY);

        model.addAttribute("featured", featured);
        model.addAttribute("recommended", recommended);
        model.addAttribute("newReleases", newReleases);

        return "discover"; 
    }

    @GetMapping("/surprise-me")
    public String surpriseMe() {
        try {
            Long randomId = movieRepository.findRandomMediaId();
            return "redirect:/media/" + randomId;
        } catch (Exception e) {
            return "redirect:/discover"; 
        }
    }

    
    
    @GetMapping("category/{genre}")
    public String getMediaByGenre(@PathVariable String genre, Model model) {
        List<Movie> moviesByGenre = movieRepository.findByGenreIgnoreCase(genre);
        List<TVShow> tvShowsByGenre = tvShowRepository.findByGenreIgnoreCase(genre);

        if ("sci-fi-fantasy".equalsIgnoreCase(genre)) {
            moviesByGenre = movieRepository.findByGenreIgnoreCase("Science Fiction");
            tvShowsByGenre = tvShowRepository.findByGenreIgnoreCase("Sci-Fi & Fantasy");
        }
        else if ("action".equalsIgnoreCase(genre)) {
            moviesByGenre = movieRepository.findByGenreIgnoreCase("Adventure");
            tvShowsByGenre = tvShowRepository.findByGenreIgnoreCase("Action & Adventure");
        }

        model.addAttribute("genre", genre);
        model.addAttribute("movies", moviesByGenre);
        model.addAttribute("tvShows", tvShowsByGenre);

        return "Discover/genre";
    }
    
    
}
