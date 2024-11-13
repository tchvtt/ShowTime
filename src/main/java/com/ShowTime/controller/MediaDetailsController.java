package com.ShowTime.controller;
import com.ShowTime.model.*;
import com.ShowTime.repository.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@Controller
public class MediaDetailsController {

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TVShowRepository tvShowRepository;

    @Autowired
    private ActorRepository actorRepository;



    @GetMapping("/media/{id}")
    public String showMedia(@PathVariable("id") Long id, Model model) {
        // Media Infos
        Media media = null;
        if (movieRepository.existsById(id)) {
            media = movieRepository.findById(id).orElse(null);
        } else if (tvShowRepository.existsById(id)) {
            media = tvShowRepository.findById(id).orElse(null);
        }
        if (media == null) {
            return "redirect:/index"; 
        }
        model.addAttribute("media", media);

       // Media Actors
        List<Actor> actors = actorRepository.findActorsByMediaId(media.getId());
        model.addAttribute("actors", actors);

        // Media Ratings
        //List<Rating> ratings = ratingRepository.findRatingsByMediaId(id);
        //model.addAttribute("ratings", ratings);

        return "Media/Details";
    }


    //Ajouter un média à une liste
    @PostMapping("/{id}/add-to-list")
    public String addMediaToList(@PathVariable("id") Long mediaId, @RequestParam("listType") String listType, @AuthenticationPrincipal User currentUser) {

        //Récupérer le média dans l'un des repositories
        Media media = movieRepository.findById(mediaId).orElse(null);
        if (media == null) {
            media = tvShowRepository.findById(mediaId).orElse(null);
        }
        if (media == null) {
            return "redirect:/"; //Redirection si le média n'existe pas
        }

        /*
        switch (listType) {
            case "favoriteMovies":
                List<FavoriteMoviesList> favoriteMoviesLists = favoriteMoviesListRepository.findByUser(currentUser);
                if (!favoriteMoviesLists.isEmpty()) {
                    FavoriteMoviesList favoriteMoviesList = favoriteMoviesLists.get(0);
                    if (!favoriteMoviesList.containsMedia(media)) {
                        favoriteMoviesList.addMedia(media);
                        favoriteMoviesListRepository.save(favoriteMoviesList);
                    }
                }
                break;
            case "favoriteTVShows":
                List<FavoriteTVShowsList> favoriteTVShowsLists = favoriteTVShowsListRepository.findByUser(currentUser);
                if (!favoriteTVShowsLists.isEmpty()) {
                    FavoriteTVShowsList favoriteTVShowsList = favoriteTVShowsLists.get(0);
                    if (!favoriteTVShowsList.containsMedia(media)) {
                        favoriteTVShowsList.addMedia(media);
                        favoriteTVShowsListRepository.save(favoriteTVShowsList);
                    }
                }
                break;
            case "toWatchMovies":
                List<ToWatchMoviesList> toWatchMoviesLists = toWatchMoviesListRepository.findByUser(currentUser);
                if (!toWatchMoviesLists.isEmpty()) {
                    ToWatchMoviesList toWatchMoviesList = toWatchMoviesLists.get(0);
                    if (!toWatchMoviesList.containsMedia(media)) {
                        toWatchMoviesList.addMedia(media);
                        toWatchMoviesListRepository.save(toWatchMoviesList);
                    }
                }
                break;
            case "toWatchTVShows":
                List<ToWatchTVShowsList> toWatchTVShowsLists = toWatchTVShowsListRepository.findByUser(currentUser);
                if (!toWatchTVShowsLists.isEmpty()) {
                    ToWatchTVShowsList toWatchTVShowsList = toWatchTVShowsLists.get(0);
                    if (!toWatchTVShowsList.containsMedia(media)) {
                        toWatchTVShowsList.addMedia(media);
                        toWatchTVShowsListRepository.save(toWatchTVShowsList);
                    }
                }
                break;
            default:
                return "redirect:/";
        }
        */

        return "redirect:/media/" + mediaId; //Retour à la page du média
    }
}
