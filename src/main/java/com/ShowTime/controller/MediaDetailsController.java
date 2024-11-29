package com.ShowTime.controller;
import com.ShowTime.model.*;
import com.ShowTime.repository.*;
import com.ShowTime.security.CustomUserDetails;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;

@Controller
public class MediaDetailsController {

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private TVShowRepository tvShowRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaListRepository mediaListRepository;



    @GetMapping("/media/{id}")
    public String showMedia(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("id") Long id, Model model) {
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
        List<Rating> ratings = ratingRepository.findRatingsByMediaId(id);
        model.addAttribute("ratings", ratings);

        // Connected User ?
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() &&
                                   !"anonymousUser".equals(authentication.getPrincipal());
        model.addAttribute("isAuthenticated", isAuthenticated);

        // Get Lists To Add the Media
        if (isAuthenticated) {
            User currentUser = userRepository.findById(customUserDetails.getUser().getId()).orElse(null);
    
            if (currentUser != null) {
                if (media instanceof Movie) {
                    model.addAttribute("watchedMovies", mediaListRepository.findByUserAndMediaListTypeAndMediaType(currentUser, MediaListType.WATCHED, MediaType.MOVIE));
                    model.addAttribute("toWatchMovies", mediaListRepository.findByUserAndMediaListTypeAndMediaType(currentUser, MediaListType.TO_WATCH, MediaType.MOVIE));
                    model.addAttribute("favoriteMovies", mediaListRepository.findByUserAndMediaListTypeAndMediaType(currentUser, MediaListType.FAVORITE, MediaType.MOVIE));
                } 
                else if (media instanceof TVShow) {
                    model.addAttribute("watchedTVShows", mediaListRepository.findByUserAndMediaListTypeAndMediaType(currentUser, MediaListType.WATCHED, MediaType.TV_SHOW));
                    model.addAttribute("toWatchTVShows", mediaListRepository.findByUserAndMediaListTypeAndMediaType(currentUser, MediaListType.TO_WATCH, MediaType.TV_SHOW));
                    model.addAttribute("favoriteTVShows", mediaListRepository.findByUserAndMediaListTypeAndMediaType(currentUser, MediaListType.FAVORITE, MediaType.TV_SHOW));
                }

                boolean isInToWatch = mediaListRepository.existsByUserAndMediaAndMediaListType(currentUser, media, MediaListType.TO_WATCH);
                boolean isInWatched = mediaListRepository.existsByUserAndMediaAndMediaListType(currentUser, media, MediaListType.WATCHED);
                boolean isInFavorite = mediaListRepository.existsByUserAndMediaAndMediaListType(currentUser, media, MediaListType.FAVORITE);
    
                model.addAttribute("isInToWatch", isInToWatch);
                model.addAttribute("isInWatched", isInWatched);
                model.addAttribute("isInFavorite", isInFavorite);

                // Vérifier si l'utilisateur a déjà ajouté un rating pour ce média
                Optional<Rating> existingRating = ratingRepository.findByUserIdAndMediaId(currentUser.getId(), media.getId());
                model.addAttribute("existingRating", existingRating.orElse(null));
            }
        }

        return "Media/Details";
    }



    /*
    @PostMapping("/media/{id}/add-to-list")
    public String addMediaToList(@PathVariable("id") Long mediaId, @RequestParam("listName") String listName, @RequestParam("mediaType") String mediaType, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {

            Media media = movieRepository.findById(mediaId).orElse(null);
            if (media == null) {
                media = tvShowRepository.findById(mediaId).orElse(null);
            }
            if (media == null) {
                return "redirect:/";
            }

            User user = userRepository.findById(customUserDetails.getUser().getId()).orElse(null);
            MediaListType listType = MediaListType.valueOf(listName);
            MediaType type = MediaType.valueOf(mediaType);
            MediaList mediaList = mediaListRepository.findByUserAndMediaListTypeAndMediaType(user, listType, type);

            mediaList.addMedia(media);
            mediaListRepository.save(mediaList);
            

            return "redirect:/media/" + mediaId;
        }

        return "redirect:/"; 
    }
    */


    @PostMapping("/media/{id}/toggle-list")
    public String toggleMediaInList(@PathVariable("id") Long mediaId, @RequestParam("listName") String listName, @RequestParam("action") String action, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {
            Media media = movieRepository.findById(mediaId).orElse(null);
            if (media == null) {
                media = tvShowRepository.findById(mediaId).orElse(null);
            }
            if (media == null) {
                return "redirect:/";
            }

            User user = userRepository.findById(customUserDetails.getUser().getId()).orElse(null);
            MediaListType listType = MediaListType.valueOf(listName);

            MediaList mediaList = mediaListRepository.findByUserAndMediaListTypeAndMediaType(user, listType, media instanceof Movie ? MediaType.MOVIE : MediaType.TV_SHOW);

            if ("add".equals(action)) {
                if (!mediaList.getMediaList().contains(media)) {
                    if (listName.equals("WATCHED")) {
                        MediaList toWatchList = mediaListRepository.findByUserAndMediaListTypeAndMediaType(user, MediaListType.TO_WATCH, media instanceof Movie ? MediaType.MOVIE : MediaType.TV_SHOW);
                        toWatchList.removeMedia(media);
                        mediaListRepository.save(toWatchList);
                    }
                    else if (listName.equals("TO_WATCH")) {
                        MediaList watchedList = mediaListRepository.findByUserAndMediaListTypeAndMediaType(user, MediaListType.WATCHED, media instanceof Movie ? MediaType.MOVIE : MediaType.TV_SHOW);
                        watchedList.removeMedia(media);
                        mediaListRepository.save(watchedList);
                    }
                    mediaList.addMedia(media);
                    mediaListRepository.save(mediaList);
                }
            } else if ("remove".equals(action)) {
                if (mediaList.getMediaList().contains(media)) {
                    mediaList.removeMedia(media);
                    mediaListRepository.save(mediaList);
                }
            }
        }

        return "redirect:/media/" + mediaId;
    }

    
    /*
    @PostMapping("media/{id}/add-rating")
    public String addRating(@PathVariable("id") Long mediaId,
                            @RequestParam("rating") int rating,
                            @RequestParam("comment") String comment,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails,
                            Model model) {

        User user = userRepository.findById(customUserDetails.getUser().getId()).orElse(null);

        Media media = movieRepository.findById(mediaId).orElse(null);
        if (media == null) {
            media = tvShowRepository.findById(mediaId).orElse(null);
        }
        if (media == null) {
            return "redirect:/";
        }

        // Création de l'objet Rating
        Rating newRating = new Rating(user, media, rating, comment);
        newRating.setDate(LocalDate.now());

        // Sauvegarde dans la base de données
        ratingRepository.save(newRating);

        // Redirection vers la page des détails du média
        return "redirect:/media/" + mediaId;
    }

    @PostMapping("media/{id}/delete-rating")
    public String deleteRating(@PathVariable("id") Long mediaId,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUser().getId();

        // Trouver et supprimer le rating existant
        Optional<Rating> existingRating = ratingRepository.findByUserIdAndMediaId(userId, mediaId);
        existingRating.ifPresent(ratingRepository::delete);

        // Redirection vers la page des détails du média
        return "redirect:/media/" + mediaId;
    }
    */

    @PostMapping("/media/{id}/toggle-rating")
    public String toggleRating(@PathVariable("id") Long mediaId,
                            @RequestParam("rating") Optional<Integer> rating,
                            @RequestParam("comment") Optional<String> comment,
                            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getUser().getId();
        User user = userRepository.findById(userId).orElse(null);
        Media media = movieRepository.findById(mediaId).orElse(null);

        if (media == null) {
            media = tvShowRepository.findById(mediaId).orElse(null);
        }
        if (media == null || user == null) {
            return "redirect:/";
        }

        Optional<Rating> existingRating = ratingRepository.findByUserIdAndMediaId(userId, mediaId);

        if (existingRating.isPresent()) {
            ratingRepository.delete(existingRating.get());
        } else if (rating.isPresent() && comment.isPresent()) {
            Rating newRating = new Rating(user, media, rating.get(), comment.get());
            newRating.setDate(LocalDate.now());
            ratingRepository.save(newRating);
        }

        return "redirect:/media/" + mediaId;
    }

}
