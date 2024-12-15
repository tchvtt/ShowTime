package com.ShowTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ShowTime.model.MediaList;
import com.ShowTime.model.MediaListType;
import com.ShowTime.model.MediaType;
import com.ShowTime.repository.ActorRepository;
import com.ShowTime.repository.MediaListRepository;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.RatingRepository;
import com.ShowTime.repository.TVShowRepository;
import com.ShowTime.repository.UserRepository;



@SpringBootApplication
public class ShowTimeApplication implements CommandLineRunner{
    public static void main(String[] args) {
      SpringApplication.run(ShowTimeApplication.class, args);
    }



    // Initialiser la bdd, a décommenter que si on veut la refill
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

        MediaList topRatedMovies = new MediaList("Top Rated Movies",MediaListType.TOP_RATED,MediaType.MOVIE);
        MediaList popularMovies = new MediaList("Popular Movies",MediaListType.POPULAR,MediaType.MOVIE);
        MediaList trendingMovies = new MediaList("Trending Movies",MediaListType.TRENDING,MediaType.MOVIE);
        MediaList allMovies = new MediaList("All Movies",MediaListType.ALL,MediaType.MOVIE);

        MediaList topRatedTVShows = new MediaList("Top Rated TVShows",MediaListType.TOP_RATED,MediaType.TV_SHOW);
        MediaList popularTVShows = new MediaList("Popular TVShows",MediaListType.POPULAR,MediaType.TV_SHOW);
        MediaList trendingTVShows = new MediaList("Trending TVShows",MediaListType.TRENDING,MediaType.TV_SHOW);
        MediaList allTVShows = new MediaList("All TVShows",MediaListType.ALL,MediaType.TV_SHOW);


        MediaList featured = new MediaList("Featured",MediaListType.FEATURED,MediaType.ANY);
        MediaList recommended = new MediaList("Recommended",MediaListType.RECOMMENDED,MediaType.ANY);
        MediaList newReleases = new MediaList("New Releases",MediaListType.NEW_RELEASES,MediaType.ANY);

        //TMDBApiClient.fillMovieDatabase(mediaListRepository,movieRepository,actorRepository,topRatedMovies,popularMovies,trendingMovies,allMovies);
        //TMDBApiClient.fillTVShowDatabase(mediaListRepository,tvshowRepository,actorRepository,topRatedTVShows,popularTVShows,trendingTVShows,allTVShows);
        //TMDBApiClient.fillDiscover(mediaListRepository,featured,recommended,newReleases,allMovies,allTVShows,movieRepository,tvshowRepository,actorRepository);

    }
}
