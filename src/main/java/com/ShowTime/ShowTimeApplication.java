package com.ShowTime;

import com.ShowTime.model.*;
import com.ShowTime.repository.ActorRepository;
import com.ShowTime.repository.MediaListRepository;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.RatingRepository;
import com.ShowTime.repository.TVShowRepository;
import com.ShowTime.repository.UserRepository;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ShowTime.TMDBApi.TMDBApiClient;
import com.ShowTime.model.Actor;
import com.ShowTime.model.MediaList;
import com.ShowTime.model.MediaListType;
import com.ShowTime.model.MediaType;
import com.ShowTime.model.Movie;
import com.ShowTime.model.TVShow;
import com.ShowTime.model.User;
import com.ShowTime.repository.ActorRepository;
import com.ShowTime.repository.MediaListRepository;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.TVShowRepository;
import com.ShowTime.repository.UserRepository;

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

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public void run(String... args) {
        //Add the movies to the db

        //MediaList creation
        MediaList allMovies = new MediaList("All Movies",MediaListType.ALL,MediaType.MOVIE);
        MediaList popularMovies = new MediaList("Popular Movies",MediaListType.POPULAR,MediaType.MOVIE);
        MediaList topRatedMovies = new MediaList("Top Rated Movies",MediaListType.TOP_RATED,MediaType.MOVIE);
        MediaList trendingMovies = new MediaList("Trending Movies",MediaListType.TRENDING,MediaType.MOVIE);

        //Get all the movies and save them to the db
        LinkedHashSet<Movie> allMoviesSet = new LinkedHashSet();
        LinkedHashSet<Movie> popularMoviesSet = new LinkedHashSet();
        LinkedHashSet<Movie> topRatedMoviesSet = new LinkedHashSet();
        LinkedHashSet<Movie> trendingMoviesSet = new LinkedHashSet();

        popularMoviesSet = TMDBApiClient.getMovieListEndpointPageN(1, TMDBApiClient.getPopularMovieEndpoint());
        topRatedMoviesSet = TMDBApiClient.getMovieListEndpointPageN(1, TMDBApiClient.getTopRatedMovieEndpoint());
        trendingMoviesSet = TMDBApiClient.getTrendingMovie();

        for (Movie currentMovie : topRatedMoviesSet) {
            movieRepository.save(currentMovie);
            topRatedMovies.getMediaList().add(currentMovie);
            allMoviesSet.add(currentMovie);
        }
        mediaListRepository.save(topRatedMovies);

        for (Movie currentMovie : popularMoviesSet) {
            movieRepository.save(currentMovie);
            popularMovies.getMediaList().add(currentMovie);
            allMoviesSet.add(currentMovie);
        }
        mediaListRepository.save(popularMovies);

        for (Movie currentMovie : trendingMoviesSet) {
            movieRepository.save(currentMovie);
            trendingMovies.getMediaList().add(currentMovie);
            allMoviesSet.add(currentMovie);
        }
        mediaListRepository.save(trendingMovies);

        for (Movie currentMovie : allMoviesSet) {
            allMovies.getMediaList().add(currentMovie);
        }
        mediaListRepository.save(allMovies);

        System.out.println("Movies added to the db");

        //Add TVShows to the db

        //MediaList creation
        MediaList allTVShows = new MediaList("All TVShows",MediaListType.ALL,MediaType.TV_SHOW);
        MediaList popularTVShows = new MediaList("Popular TVShows",MediaListType.POPULAR,MediaType.TV_SHOW);
        MediaList topRatedTVShows = new MediaList("Top Rated TVShows",MediaListType.TOP_RATED,MediaType.TV_SHOW);
        MediaList trendingTVShows = new MediaList("Trending TVShows",MediaListType.TRENDING,MediaType.TV_SHOW);

        //Get all the movies and save them to the db
        LinkedHashSet<TVShow> allTVShowsSet = new LinkedHashSet();
        LinkedHashSet<TVShow> popularTVShowsSet = new LinkedHashSet();
        LinkedHashSet<TVShow> topRatedTVShowsSet = new LinkedHashSet();
        LinkedHashSet<TVShow> trendingTVShowsSet = new LinkedHashSet();

        popularTVShowsSet = TMDBApiClient.getTVShowListEndpointPageN(1, TMDBApiClient.getPopularTVShowEndpoint());
        topRatedTVShowsSet = TMDBApiClient.getTVShowListEndpointPageN(1, TMDBApiClient.getTopRatedTVShowEndpoint());
        trendingTVShowsSet = TMDBApiClient.getTrendingTVShow();

        for (TVShow currentTVShow : topRatedTVShowsSet) {
            tvshowRepository.save(currentTVShow);
            topRatedTVShows.getMediaList().add(currentTVShow);
            allTVShowsSet.add(currentTVShow);
        }
        mediaListRepository.save(topRatedTVShows);


        for (TVShow currentTVShow : popularTVShowsSet) {
            tvshowRepository.save(currentTVShow);
            popularTVShows.getMediaList().add(currentTVShow);
            allTVShowsSet.add(currentTVShow);
        }
        mediaListRepository.save(popularTVShows);

        for (TVShow currentTVShow : trendingTVShowsSet) {
            tvshowRepository.save(currentTVShow);
            trendingTVShows.getMediaList().add(currentTVShow);
            allTVShowsSet.add(currentTVShow);
        }
        mediaListRepository.save(trendingTVShows);

        for (TVShow currentTVShow : allTVShowsSet) {
            allTVShows.getMediaList().add(currentTVShow);
        }
        mediaListRepository.save(allTVShows);

        System.out.println("TVShows added to the db");

        /*


      // Création des acteurs de Friends
      Actor actor1 = new Actor("Jennifer Aniston", LocalDate.of(1969, 2, 11));
      Actor actor2 = new Actor("Courteney Cox", LocalDate.of(1964, 6, 15));
      Actor actor3 = new Actor("Lisa Kudrow", LocalDate.of(1963, 7, 30));
      Actor actor4 = new Actor("Matt LeBlanc", LocalDate.of(1967, 7, 25));
      Actor actor5 = new Actor("Matthew Perry", LocalDate.of(1969, 8, 19));
      Actor actor6 = new Actor("David Schwimmer", LocalDate.of(1966, 11, 2));


      // Associer les acteurs à la MediaList de Friends
      actor1.addMedia(tvshow3);
      actor2.addMedia(tvshow3);
      actor3.addMedia(tvshow3);
      actor4.addMedia(tvshow3);
      actor5.addMedia(tvshow3);
      actor6.addMedia(tvshow3);


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







        // Création de ratings
        Rating rating1 = new Rating(thomas, tvshow3, 5, "Amazing");
        ratingRepository.save(rating1);

        Rating rating2 = new Rating(thomas, tvshow1, 4, "Still Viewing");
        ratingRepository.save(rating2);
*/
    }
}