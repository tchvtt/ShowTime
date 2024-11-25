/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

 package com.ShowTime.TMDBApi;

 //imports to make the request
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import com.ShowTime.ShowTimeApplication;
import com.ShowTime.controller.ActorDetailsController;
import com.ShowTime.model.*;
import com.ShowTime.repository.ActorRepository;
import com.ShowTime.repository.MediaListRepository;
import com.ShowTime.repository.MovieRepository;
import com.ShowTime.repository.TVShowRepository;
import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;


/**
  *
  * @author anthony
  */
 public class TMDBApiClient {

    private static final String API_KEY = "xxx";
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String BASE_IMAGE_URL = "https://media.themoviedb.org/t/p/original";

    private static final String TOP_RATED_MOVIE = "movie/top_rated?language=en-US&page=";
    private static final String POPULAR_MOVIE = "movie/popular?language=en-US&page=";
    private static final String TRENDING_MOVIE = "trending/movie/week?language=en-US";

    private static final String TOP_RATED_TVSHOW = "tv/top_rated?language=en-US&page=";
    private static final String POPULAR_TVSHOW = "tv/popular?language=en-US&page=";
    private static final String TRENDING_TVSHOW = "trending/tv/week?language=en-US";



    public static String makeRequest(String endpoint) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET()
                .header("accept", "application/json")
                .header("Authorization", API_KEY)
                .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new AuthenticationException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray handleApiArrayResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.optJSONArray("results");
        return results;
    }

    public static JSONObject handleApiResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse;
    }

    public static LinkedHashSet<Integer> handleMediaList(JSONArray results) {
        JSONObject currentMedia;
        LinkedHashSet<Integer> mediaIDSet = new LinkedHashSet<>();
        for (int i = 0; i < results.length(); i++) {
            currentMedia = results.getJSONObject(i);
            mediaIDSet.add(currentMedia.getInt("id"));
        }
        return mediaIDSet;
    }

    public static String handleGenre(JSONObject response) {
        JSONArray genresArray;
        JSONObject genre;
        genresArray = response.optJSONArray("genres");
        if (genresArray != null && !genresArray.isEmpty()) {
            genre = genresArray.getJSONObject(0);
            return genre.getString("name");
        } else {
            return "unknown";
        }
    }

    public static List<Integer> handleActorsList(String response) {
        JSONObject actor;
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray responseArray = jsonResponse.getJSONArray("cast");
        List<Integer> actorSet = new ArrayList<>();
        if (responseArray != null && !responseArray.isEmpty()) {
            for (int i = 0; i < responseArray.length(); i++) {
                actor = responseArray.getJSONObject(i);
                if (actor.getString("known_for_department").equals("Acting")) {
                    if (actorSet.size() < 11) {
                        actorSet.add(actor.getInt("id"));
                    } else {
                        break;
                    }
                }
            }
        }
        return actorSet;
    }

    public static Actor getActorInfo(ActorRepository actorRepository, int actorID) {
        if (actorRepository.existsActorByTmdbID(actorID)) {
            return actorRepository.findActorByTmdbID(actorID);
        } else {
            JSONObject jsonObject;
            jsonObject = handleApiResponse(makeRequest("person/" + actorID));
            Actor actor = new Actor(actorID);
            System.out.println("Handling actor :" + jsonObject.optString("name","unknown"));
            actor.setName(jsonObject.optString("name","unknown"));
            actor.setBirthDate(LocalDate.parse(jsonObject.optString("birth_date",LocalDate.now().toString())));
            actor.setPosterURL(BASE_IMAGE_URL + jsonObject.optString("poster_path","null"));
            return actor;
        }
    }


    public static Movie handleMovie(MovieRepository movieRepository, int movieID, ActorRepository actorRepository) {
        if (movieRepository.existsMovieByTmdbID(movieID)) {
            return movieRepository.findMovieByTmdbID(movieID);
        } else {
            JSONObject jsonObject;
            jsonObject = handleApiResponse(makeRequest("movie/" + movieID));
            Movie movie = new Movie(movieID);
            System.out.println("Handling movie: " + jsonObject.optString("title","unknown"));
            movie.setTitle(jsonObject.optString("title","unknown"));
            movie.setDuration(jsonObject.optDouble("runtime",0.0));
            movie.setReleaseDate(LocalDate.parse(jsonObject.optString("release_date",LocalDate.now().toString())));
            movie.setGenre(handleGenre(jsonObject));
            movie.setOverview(jsonObject.optString("overview","unknown"));
            movie.setPosterURL(BASE_IMAGE_URL + jsonObject.optString("poster_path","null"));
            movie.setActorsID(handleActorsList(makeRequest("movie/" + movieID + "/credits")));
            System.out.println("Handling cast of movie: " + jsonObject.optString("title"));
            for (Integer currentActorID : movie.getActorsID()) {
                movie.addActor(getActorInfo(actorRepository, currentActorID));
            }
            for (Actor currentActor : movie.getActors()) {
                currentActor.addMedia(movie);
                actorRepository.save(currentActor);
            }
            movieRepository.save(movie);
            return movie;

        }
    }

    public static TVShow handleTVShow(TVShowRepository tvShowRepository, int tvShowID, ActorRepository actorRepository) {
        if (tvShowRepository.existsTVShowByTmdbID(tvShowID)) {
            return tvShowRepository.findTVShowByTmdbID(tvShowID);
        } else {
            JSONObject jsonObject;
            jsonObject = handleApiResponse(makeRequest("tv/" + tvShowID));
            TVShow tvShow = new TVShow(tvShowID);
            System.out.println("Handling tv show: " + jsonObject.optString("name"));
            tvShow.setTitle(jsonObject.optString("name"));
            tvShow.setReleaseDate(LocalDate.parse(jsonObject.optString("first_air_date")));
            tvShow.setNumberOfSeasons(jsonObject.optInt("number_of_seasons"));
            tvShow.setGenre(handleGenre(jsonObject));
            tvShow.setOverview(jsonObject.optString("overview"));
            tvShow.setPosterURL(BASE_IMAGE_URL + jsonObject.opt("poster_path"));
            tvShow.setActorsID(handleActorsList(makeRequest("tv/" + tvShowID + "/aggregate_credits")));
            System.out.println("Handling cast of tv show: " + jsonObject.getString("name"));
            for (Integer currentActorID : tvShow.getActorsID()) {
                tvShow.addActor(getActorInfo(actorRepository, currentActorID));
            }
            for (Actor currentActor : tvShow.getActors()) {
                currentActor.addMedia(tvShow);
                actorRepository.save(currentActor);
            }
            tvShowRepository.save(tvShow);
            return tvShow;
        }
    }

    public static LinkedHashSet<Movie> aggregateMovies(LinkedHashSet<Integer> movieIDs, MovieRepository movieRepository, ActorRepository actorRepository) {
        LinkedHashSet<Movie> movieSet = new LinkedHashSet<>();
        for (Integer currentMovieID : movieIDs) {
            movieSet.add(handleMovie(movieRepository, currentMovieID, actorRepository));
        }
        return movieSet;
    }

    public static LinkedHashSet<TVShow> aggregateTVShows(LinkedHashSet<Integer> tvShowIDs, TVShowRepository tvShowRepository, ActorRepository actorRepository) {
        LinkedHashSet<TVShow> tvShowSet = new LinkedHashSet<>();
        for (Integer currentTVShowID : tvShowIDs) {
            tvShowSet.add(handleTVShow(tvShowRepository, currentTVShowID, actorRepository));
        }
        return tvShowSet;
    }

    public static LinkedHashSet<Movie> aggregateMovieSet(LinkedHashSet<Movie> destination, LinkedHashSet<Movie> source) {
        for (Movie currentMovie : source) {
            destination.add(currentMovie);
        }
        return destination;
    }

    public static LinkedHashSet<TVShow> aggregateTVShowSet(LinkedHashSet<TVShow> destination, LinkedHashSet<TVShow> source) {
        for (TVShow currentTVShow : source) {
            destination.add(currentTVShow);
        }
        return destination;
    }

    public static LinkedHashSet<Movie> getMovieListEndpointPageN(int pageN, String endpoint, MovieRepository movieRepository, ActorRepository actorRepository) {
        LinkedHashSet<Movie> movieSet = new LinkedHashSet<>();
        movieSet = aggregateMovies(handleMediaList(handleApiArrayResponse(makeRequest(endpoint + pageN))), movieRepository, actorRepository);
        return movieSet;
    }

    public static LinkedHashSet<TVShow> getTVShowListEndpointPageN(int pageN, String endpoint, TVShowRepository tvShowRepository, ActorRepository actorRepository) {
        LinkedHashSet<TVShow> tvShowSet = new LinkedHashSet<>();
        tvShowSet = aggregateTVShows(handleMediaList(handleApiArrayResponse(makeRequest(endpoint + pageN))), tvShowRepository, actorRepository);
        return tvShowSet;
    }

    public static LinkedHashSet<Movie> getTrendingMovie(MovieRepository movieRepository, ActorRepository actorRepository) {
        LinkedHashSet<Movie> movieSet = new LinkedHashSet<>();
        movieSet = aggregateMovies(handleMediaList(handleApiArrayResponse(makeRequest("trending/movie/week?language=en-US"))),movieRepository,actorRepository);
        return movieSet;
    }

    public static LinkedHashSet<TVShow> getTrendingTVShow(TVShowRepository tvShowRepository, ActorRepository actorRepository) {
        LinkedHashSet<TVShow> tvShowSet = new LinkedHashSet<>();
        tvShowSet = aggregateTVShows(handleMediaList(handleApiArrayResponse(makeRequest("trending/tv/week?language=en-US"))), tvShowRepository, actorRepository);
        return tvShowSet;
    }


    public static void fillMovieDatabase(MediaListRepository MediaListRepository, MovieRepository movieRepository, ActorRepository actorRepository, MediaList topRatedMovies, MediaList popularMovies, MediaList trendingMovies, MediaList allMovies) {
        LinkedHashSet<Movie> popularMoviesSet;
        LinkedHashSet<Movie> topRatedMoviesSet;
        LinkedHashSet<Movie> trendingMoviesSet;

        popularMoviesSet = getMovieListEndpointPageN(1, getPopularMovieEndpoint(), movieRepository, actorRepository);
        topRatedMoviesSet = getMovieListEndpointPageN(1, getTopRatedMovieEndpoint(), movieRepository, actorRepository);
        trendingMoviesSet = getTrendingMovie(movieRepository, actorRepository);

        for (Movie currentMovie : topRatedMoviesSet) {
            topRatedMovies.getMediaList().add(currentMovie);
        }
        MediaListRepository.save(topRatedMovies);


        for (Movie currentMovie : popularMoviesSet) {
            popularMovies.getMediaList().add(currentMovie);
        }
        MediaListRepository.save(popularMovies);

        for (Movie currentMovie : trendingMoviesSet) {
            trendingMovies.getMediaList().add(currentMovie);
        }
        MediaListRepository.save(trendingMovies);

        for (Movie currentMovie : movieRepository.findAll()) {
            allMovies.getMediaList().add(currentMovie);
        }
        MediaListRepository.save(allMovies);

    }
    public static void fillTVShowDatabase(MediaListRepository MediaListRepository, TVShowRepository tvShowRepository, ActorRepository actorRepository, MediaList topRatedTVShows, MediaList popularTVShows, MediaList trendingTVShows, MediaList allTVShows) {

        LinkedHashSet<TVShow> popularTVShowsSet;
        LinkedHashSet<TVShow> topRatedTVShowsSet;
        LinkedHashSet<TVShow> trendingTVShowsSet;

        popularTVShowsSet = getTVShowListEndpointPageN(1, getPopularTVShowEndpoint(), tvShowRepository, actorRepository);
        topRatedTVShowsSet = getTVShowListEndpointPageN(1, getTopRatedTVShowEndpoint(), tvShowRepository, actorRepository);
        trendingTVShowsSet = getTrendingTVShow(tvShowRepository, actorRepository);

        for (TVShow currentTVShow : topRatedTVShowsSet) {
            topRatedTVShows.getMediaList().add(currentTVShow);
        }
        MediaListRepository.save(topRatedTVShows);


        for (TVShow currentTVShow : popularTVShowsSet) {
            popularTVShows.getMediaList().add(currentTVShow);
        }
        MediaListRepository.save(popularTVShows);

        for (TVShow currentTVShow : trendingTVShowsSet) {
            trendingTVShows.getMediaList().add(currentTVShow);
        }
        MediaListRepository.save(trendingTVShows);

        for (TVShow currentTVShow : tvShowRepository.findAll()) {
            allTVShows.getMediaList().add(currentTVShow);
        }
        MediaListRepository.save(allTVShows);
    }

    //Endpoints getters
    public static String getTopRatedMovieEndpoint() {
        return TOP_RATED_MOVIE;
    }

    public static String getPopularMovieEndpoint() {
        return POPULAR_MOVIE;
    }

    public static String getTopRatedTVShowEndpoint() {
        return TOP_RATED_TVSHOW;
    }

    public static String getPopularTVShowEndpoint() {
        return POPULAR_TVSHOW;
    }

}
 