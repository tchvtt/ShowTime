package com.ShowTime.controller;
import com.ShowTime.model.*;
import com.ShowTime.repository.*;
import com.ShowTime.security.CustomUserDetails;

import java.util.List;

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
            }
        }

        return "Media/Details";
    }



    
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
    
}
