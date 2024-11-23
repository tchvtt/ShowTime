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
import java.util.LinkedHashSet;

import com.ShowTime.model.Actor;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ShowTime.model.Movie;
import com.ShowTime.model.TVShow;
 /**
  *
  * @author anthony
  */
 public class TMDBApiClient{
 
     private static final String API_KEY = "xxx";
     private static final String BASE_URL = "https://api.themoviedb.org/3/";
     private static final String BASE_IMAGE_URL= "https://media.themoviedb.org/t/p/original";

     private static final String TOP_RATED_MOVIE = "movie/top_rated?language=en-US&page=";
     private static final String POPULAR_MOVIE = "movie/popular?language=en-US&page=";
     private static final String TRENDING_MOVIE = "trending/movie/week?language=en-US";

     private static final String TOP_RATED_TVSHOW = "tv/top_rated?language=en-US&page=";
     private static final String POPULAR_TVSHOW = "tv/popular?language=en-US&page=";
     private static final String TRENDING_TVSHOW = "trending/tv/week?language=en-US";

     public static String makeRequest(String endpoint){
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + endpoint))
            .GET()
            .header("accept", "application/json")
            .header("Authorization", API_KEY)
            .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
     }

     public static JSONArray handleApiArrayResponse(String response){
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.optJSONArray("results");
        return results;
     }

     public static JSONObject handleApiResponse(String response){
        JSONObject jsonResponse = new JSONObject(response);
        return jsonResponse;
     }

     public static LinkedHashSet<Integer> handleMediaList(JSONArray results){
        JSONObject currentMedia;
        LinkedHashSet<Integer> mediaIDSet = new LinkedHashSet<>();
        for (int i = 0; i < results.length(); i++) {
            currentMedia = results.getJSONObject(i);
            mediaIDSet.add(currentMedia.getInt("id"));
        }
        return mediaIDSet;
     }
 
     public static String handleGenre(JSONObject response){
        JSONArray genresArray;
        JSONObject genre;
        genresArray = response.optJSONArray("genres");
        if (genresArray != null && genresArray.length() > 0) {
            genre = genresArray.getJSONObject(0);
            return genre.getString("name");
        } else {
            return "unknown";
        }
     }

     public static LinkedHashSet<Actor> handleActorsList(JSONArray response){
         JSONArray actorsArray;
         JSONObject actor;
         LinkedHashSet<Actor> actorSet = new LinkedHashSet<>();
         actorsArray = response.
         if (actorsArray != null && actorsArray.length() > 0) {
             for (int i = 0; i < actorsArray.length(); i++) {
                 actor = actorsArray.getJSONObject(i);
                 if (actor.getString("known_for_department").equals("Acting")){
                     actorSet.add(new Actor(actor.getInt("id")));
                 }
             }
         }
         return actorSet;
     }

     public static void getActorInfo(Actor actor){
         JSONObject jsonObject;
         System.out.println(actor.getId());
         jsonObject = handleApiResponse(makeRequest("person/"+actor.getId()));
         System.out.println(jsonObject.toString());
         actor.setName(jsonObject.optString("name"));
         actor.setBirthDate(LocalDate.parse(jsonObject.getString("birthday")));
         actor.setPosterURL(BASE_IMAGE_URL+jsonObject.get("profile_path"));
     }

     public static Movie handleMovie(Integer movieID){
        JSONObject jsonObject;
        Movie movie = new Movie();
        jsonObject = handleApiResponse(makeRequest("movie/"+movieID));
        movie.setTitle(jsonObject.getString("title"));
        movie.setDuration(jsonObject.getDouble("runtime"));
        movie.setReleaseDate(LocalDate.parse(jsonObject.getString("release_date")));
        movie.setGenre(handleGenre(jsonObject));
        movie.setOverview(jsonObject.getString("overview"));
        movie.setPosterURL(BASE_IMAGE_URL+jsonObject.get("poster_path"));
        movie.setActors(handleActorsList(handleApiArrayResponse(makeRequest("movie/"+movieID+"/credits"))));
        System.out.println(movie.getActors().size());
        for (Actor actor : movie.getActors()){
            getActorInfo(actor);
        }
        return movie;
    }

    public static TVShow handleTVShow(Integer tvShowID){
        JSONObject jsonObject;
        TVShow tvShow = new TVShow();
        jsonObject = handleApiResponse(makeRequest("tv/"+tvShowID));
        tvShow.setTitle(jsonObject.getString("name"));
        tvShow.setNumberOfSeasons(jsonObject.getInt("number_of_seasons"));
        tvShow.setReleaseDate(LocalDate.parse(jsonObject.getString("first_air_date")));
        tvShow.setGenre(handleGenre(jsonObject));
        tvShow.setCompleted(jsonObject.getString("status").equals("Ended"));
        tvShow.setOverview(jsonObject.getString("overview"));
        tvShow.setPosterURL(BASE_IMAGE_URL+jsonObject.get("poster_path"));
        tvShow.setActors(handleActorsList(handleApiResponse(makeRequest("tv/"+tvShowID+"/aggregate_credits"))));
        for (Actor actor : tvShow.getActors()) {
            getActorInfo(actor);
        }
        return tvShow;
    }

    public static LinkedHashSet<Movie> aggregateMovies(LinkedHashSet<Integer> movieIDs){
        LinkedHashSet<Movie> movieSet = new LinkedHashSet<>();
        for (Integer currentMovieID : movieIDs) {
            movieSet.add(handleMovie(currentMovieID));
        }
        return movieSet;
    }

    public static LinkedHashSet<TVShow> aggregateTVShows(LinkedHashSet<Integer> tvShowIDs){
        LinkedHashSet<TVShow> tvShowSet = new LinkedHashSet<>();
        for (Integer currentTVShowID : tvShowIDs) {
            tvShowSet.add(handleTVShow(currentTVShowID));
        }
        return tvShowSet;
    }

    public static LinkedHashSet<Movie> aggregateMovieSet(LinkedHashSet<Movie> destination, LinkedHashSet<Movie> source){
        for (Movie currentMovie : source) {
            destination.add(currentMovie);
        }
        return destination;
    }

    public static LinkedHashSet<TVShow> aggregateTVShowSet(LinkedHashSet<TVShow> destination, LinkedHashSet<TVShow> source){
        for (TVShow currentTVShow : source) {
            destination.add(currentTVShow);
        }
        return destination;
    }

    public static LinkedHashSet<Movie> getMovieListEndpointPageN(int pageN, String endpoint){
        LinkedHashSet<Movie> movieSet = new LinkedHashSet<>();
        movieSet = aggregateMovies(handleMediaList(handleApiArrayResponse(makeRequest(endpoint+pageN))));
        return movieSet;
    }

    public static LinkedHashSet<TVShow> getTVShowListEndpointPageN(int pageN, String endpoint){
        LinkedHashSet<TVShow> tvShowSet = new LinkedHashSet<>();
        tvShowSet = aggregateTVShows(handleMediaList(handleApiArrayResponse(makeRequest(endpoint+pageN))));
        return tvShowSet;
    }

    public static LinkedHashSet<Movie> getTrendingMovie(){
        LinkedHashSet<Movie> movieSet = new LinkedHashSet<>();
        movieSet = aggregateMovies(handleMediaList(handleApiArrayResponse(makeRequest("trending/movie/week?language=en-US"))));
        return movieSet;
    }

    public static LinkedHashSet<TVShow> getTrendingTVShow(){
        LinkedHashSet<TVShow> tvShowSet = new LinkedHashSet<>();
        tvShowSet = aggregateTVShows(handleMediaList(handleApiArrayResponse(makeRequest("trending/tv/week?language=en-US"))));
        return tvShowSet;
    }

    //Endpoints getters
    public static String getTopRatedMovieEndpoint(){
        return TOP_RATED_MOVIE;
    }

    public static String getPopularMovieEndpoint(){
        return POPULAR_MOVIE;
    }

    public static String getTopRatedTVShowEndpoint(){
        return TOP_RATED_TVSHOW;
    }

    public static String getPopularTVShowEndpoint(){
        return POPULAR_TVSHOW;
    }

 }
 