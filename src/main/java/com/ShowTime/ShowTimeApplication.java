package com.ShowTime;

import com.ShowTime.model.*;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.TVShowRepository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ShowTimeApplication implements CommandLineRunner{
    public static void main(String[] args) {
      SpringApplication.run(ShowTimeApplication.class, args);
    }



    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TVShowRepository tvshowRepository;

    @Override
    public void run(String... args) {
      // Ajout de films
        Movie movie1 = new Movie("Inception", "Science Fiction", LocalDate.of(2010, 7, 16), 148.0);
        Movie movie2 = new Movie("The Matrix", "Action", LocalDate.of(1999, 3, 31), 136.0);
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        // Ajout de séries
        TVShow tvshow1 = new TVShow("Breaking Bad", "Crime", LocalDate.of(2008, 1, 20), 5, false);
        TVShow tvshow2 = new TVShow("Stranger Things", "Sci-Fi", LocalDate.of(2016, 7, 15), 4, true);
        tvshowRepository.save(tvshow1);
        tvshowRepository.save(tvshow2);
    }
}
