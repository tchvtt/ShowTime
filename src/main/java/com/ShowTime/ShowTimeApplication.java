package com.ShowTime;

import com.ShowTime.model.*;
import com.ShowTime.repository.ActorRepository;
import com.ShowTime.repository.MediaListRepository;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.TVShowRepository;
import com.ShowTime.repository.UserRepository;

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
    private MovieRepository movieRepository;

    @Autowired
    private TVShowRepository tvshowRepository;

    @Autowired
    private MediaListRepository mediaListRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
      // Ajout des films
      Movie movie1 = new Movie("Inception", "Science Fiction", LocalDate.of(2010, 7, 16), 148.0);
      Movie movie2 = new Movie("The Matrix", "Action", LocalDate.of(1999, 3, 31), 136.0);
      Movie movie3 = new Movie("Interstellar", "Science Fiction", LocalDate.of(2014, 11, 7), 169.0);
      movieRepository.save(movie1);
      movieRepository.save(movie2);
      movieRepository.save(movie3); 
  
      // Ajout de séries
      TVShow tvshow1 = new TVShow("Breaking Bad", "Crime", LocalDate.of(2008, 1, 20), 5, false);
      TVShow tvshow2 = new TVShow("Stranger Things", "Sci-Fi", LocalDate.of(2016, 7, 15), 4, true);
      TVShow tvshow3 = new TVShow("Friends", "Comedy", LocalDate.of(1994, 9, 22), 10, true);
      tvshowRepository.save(tvshow1);
      tvshowRepository.save(tvshow2);
      tvshowRepository.save(tvshow3);



      // Création des listes pour les films
      MediaList trendingMovies = new MediaList("Trending Movies", MediaListType.TRENDING, MediaType.MOVIE);
      trendingMovies.getMediaList().add(movie1);  // Ajout d'un film "Trending"
      
      MediaList mostWatchedMovies = new MediaList("Most Watched Movies", MediaListType.MOST_WATCHED, MediaType.MOVIE);
      mostWatchedMovies.getMediaList().add(movie2);  // Ajout d'un film "Most Watched"
      
      MediaList topRatedMovies = new MediaList("Top Rated Movies", MediaListType.TOP_RATED, MediaType.MOVIE);
      topRatedMovies.getMediaList().add(movie3);  // Ajout d'un film "Top Rated"

      // Création des listes pour les séries
      MediaList trendingTVShows = new MediaList("Trending TV Shows", MediaListType.TRENDING, MediaType.TV_SHOW);
      trendingTVShows.getMediaList().add(tvshow1);  // Ajout d'une série "Trending"
      
      MediaList mostWatchedTVShows = new MediaList("Most Watched TV Shows", MediaListType.MOST_WATCHED, MediaType.TV_SHOW);
      mostWatchedTVShows.getMediaList().add(tvshow2);  // Ajout d'une série "Most Watched"
      
      MediaList topRatedTVShows = new MediaList("Top Rated TV Shows", MediaListType.TOP_RATED, MediaType.TV_SHOW);
      topRatedTVShows.getMediaList().add(tvshow3);  // Ajout d'une série "Top Rated"

      // Sauvegarde des listes
      mediaListRepository.save(trendingMovies);
      mediaListRepository.save(mostWatchedMovies);
      mediaListRepository.save(topRatedMovies);
      mediaListRepository.save(trendingTVShows);
      mediaListRepository.save(mostWatchedTVShows);
      mediaListRepository.save(topRatedTVShows);







      // Création des acteurs de Friends
      Actor actor1 = new Actor("Jennifer Aniston", LocalDate.of(1969, 2, 11));
      Actor actor2 = new Actor("Courteney Cox", LocalDate.of(1964, 6, 15));
      Actor actor3 = new Actor("Lisa Kudrow", LocalDate.of(1963, 7, 30));
      Actor actor4 = new Actor("Matt LeBlanc", LocalDate.of(1967, 7, 25));
      Actor actor5 = new Actor("Matthew Perry", LocalDate.of(1969, 8, 19));
      Actor actor6 = new Actor("David Schwimmer", LocalDate.of(1966, 11, 2));

      /*
      // Associer les acteurs à la MediaList de Friends
      actor1.addMedia(tvshow3);
      actor2.addMedia(tvshow3);
      actor3.addMedia(tvshow3);
      actor4.addMedia(tvshow3);
      actor5.addMedia(tvshow3);
      actor6.addMedia(tvshow3);
      */

      // Associer les acteurs à la MediaList de Friends (tvshow3)
      
      tvshow3.addActor(actor1);
      tvshow3.addActor(actor2);
      tvshow3.addActor(actor3);
      tvshow3.addActor(actor4);
      tvshow3.addActor(actor5);
      tvshow3.addActor(actor6);
      

      // Sauvegarder les acteurs
      actorRepository.save(actor1);
      actorRepository.save(actor2);
      actorRepository.save(actor3);
      actorRepository.save(actor4);
      actorRepository.save(actor5);
      actorRepository.save(actor6);

      tvshowRepository.save(tvshow3);





          // Création de l'utilisateur
      User thomas = new User("Thomas", "thomas@example.com", "password");
        
      
      // Ajout des films et séries à ses listes
      thomas.getMediaLists().stream().forEach(list -> {
          switch (list.getName()) {
              case "Favorites TV Shows":
                  list.getMediaList().add(tvshow3); // Friends
                  break;
              case "To Watch TV Shows":
                  list.getMediaList().add(tvshow1); // Breaking Bad
                  list.getMediaList().add(tvshow2); // Stranger Things
                  break;
              case "Watched Movies":
                  list.getMediaList().add(movie3); // Interstellar
                  list.getMediaList().add(movie2); // The Matrix
                  break;
              case "To Watch Movies":
                  list.getMediaList().add(movie1); // Inception
                  break;
              default:
                  break;
          }
      });
      

      // Sauvegarde de l'utilisateur
      userRepository.save(thomas);

    }
}
