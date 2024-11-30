package com.ShowTime.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ShowTime.repository.ActorRepository;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.TVShowRepository;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TVShowRepository tvShowRepository;

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> search(@RequestParam("q") String query) {
        List<Map<String, Object>> results = new ArrayList<>();

        // Rechercher des films
        movieRepository.findByTitleContainingIgnoreCase(query).forEach(movie -> {
            Map<String, Object> movieResult = new HashMap<>();
            movieResult.put("type", "movie");
            movieResult.put("id", movie.getId());
            movieResult.put("title", movie.getTitle());
            results.add(movieResult);
        });

        // Rechercher des séries
        tvShowRepository.findByTitleContainingIgnoreCase(query).forEach(tvShow -> {
            Map<String, Object> tvShowResult = new HashMap<>();
            tvShowResult.put("type", "tvshow");
            tvShowResult.put("id", tvShow.getId());
            tvShowResult.put("title", tvShow.getTitle());
            results.add(tvShowResult);
        });

        // Rechercher des acteurs
        actorRepository.findByNameContainingIgnoreCase(query).forEach(actor -> {
            Map<String, Object> actorResult = new HashMap<>();
            actorResult.put("type", "actor");
            actorResult.put("id", actor.getId());
            actorResult.put("name", actor.getName());
            results.add(actorResult);
        });

        return ResponseEntity.ok(results);
    }

}

