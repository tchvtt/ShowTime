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
public class MediaController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TVShowRepository tvShowRepository;
    /*@Autowired
    private ActorRepository actorRepository;
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private FavoriteMoviesListRepository favoriteMoviesListRepository;
    @Autowired
    private FavoriteTVShowsListRepository favoriteTVShowsListRepository;
    @Autowired
    private ToWatchMoviesListRepository toWatchMoviesListRepository;
    @Autowired
    private ToWatchTVShowsListRepository toWatchTVShowsListRepository;*/


    //Méthode pour la page d'accueil
    @GetMapping("/index")
    public String showIndex(Model model) {
        List<Movie> popularMovies = movieRepository.findAll();
        List<TVShow> popularTVShows = tvShowRepository.findAll();
        
        model.addAttribute("popularMovies", popularMovies);
        model.addAttribute("popularTVShows", popularTVShows);
        System.out.println("Nombre de films trouvés : " + popularMovies.size());
        System.out.println("Nombre de séries trouvées : " + popularTVShows.size());
        return "index"; 
    }

    //Méthode pour avoir les détails d'un media 
    @GetMapping("/media/{id}")
    public String showMedia(@PathVariable("id") Long id, Model model) {
        Media media = null;
        if (movieRepository.existsById(id)) {
            media = movieRepository.findById(id).orElse(null);
        } else if (tvShowRepository.existsById(id)) {
            media = tvShowRepository.findById(id).orElse(null);
        }

        if (media == null) {
            return "redirect:/"; 
        }

        model.addAttribute("media", media);

       //récup les acteurs associés au média
        //List<Actor> actors = actorRepository.findActorsByMediaId(id);
        //model.addAttribute("actors", actors);

        //récup les évaluations associées au média
        //List<Rating> ratings = ratingRepository.findRatingsByMediaId(id);
        //model.addAttribute("ratings", ratings);

        return "media-details";
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
