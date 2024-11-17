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
import java.util.HashSet;
import java.util.Set;

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

     public static Set<Integer> handleMediaList(JSONArray results){
        JSONObject currentMedia;
        Set<Integer> mediaIDSet = new HashSet<>();
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
        genre = genresArray.getJSONObject(0);
        return genre.getString("name");
     }

     public static Movie handleMovie(Integer movieID){
        JSONObject jsonObject;
        Movie movie = new Movie();
        jsonObject = handleApiResponse(makeRequest("movie/"+movieID));
        movie.setTitle(jsonObject.getString("title"));
        movie.setDuration(jsonObject.getDouble("runtime"));
        movie.setReleaseDate(LocalDate.parse(jsonObject.getString("release_date")));
        movie.setGenre(handleGenre(jsonObject));
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
        return tvShow;
    }

    public static Set<Movie> aggregateMovies(Set<Integer> movieIDs){
        Set<Movie> movieSet = new HashSet<>();
        for (Integer currentMovieID : movieIDs) {
            movieSet.add(handleMovie(currentMovieID));
        }
        return movieSet;
    }

    public static Set<TVShow> aggregateTVShows(Set<Integer> tvShowIDs){
        Set<TVShow> tvShowSet = new HashSet<>();
        for (Integer currentTVShowID : tvShowIDs) {
            tvShowSet.add(handleTVShow(currentTVShowID));
        }
        return tvShowSet;
    }

    public static Set<Movie> aggregateMovieSet(Set<Movie> destination, Set<Movie> source){
        for (Movie currentMovie : source) {
            destination.add(currentMovie);
        }
        return destination;
    }

    public static Set<TVShow> aggregateTVShowSet(Set<TVShow> destination, Set<TVShow> source){
        for (TVShow currentTVShow : source) {
            destination.add(currentTVShow);
        }
        return destination;
    }

    public static Set<Movie> getTopRatedMoviePageN(int pageN){
        Set<Movie> movieSet = new HashSet<>();
        movieSet = aggregateMovies(handleMediaList(handleApiArrayResponse(makeRequest("movie/top_rated?language=en-US&page="+pageN))));
        return movieSet;
    }

    public static Set<TVShow> getTopRatedTVShowPageN(int pageN){
        Set<TVShow> tvShowSet = new HashSet<>();
        tvShowSet = aggregateTVShows(handleMediaList(handleApiArrayResponse(makeRequest("tv/top_rated?language=en-US&page="+pageN))));
        return tvShowSet;
    }

    public static Set<Movie> getTop100Movie(){
        Set <Movie> movieSet = new HashSet<>();
        for(int i = 1; i < 6; i++){
            movieSet = aggregateMovieSet(movieSet, getTopRatedMoviePageN(i));
        }
        return movieSet;
    }

    public static Set<TVShow> getTop100TVShow(){
        Set <TVShow> tvShowSet = new HashSet<>();
        for(int i = 1; i < 6; i++){
            tvShowSet = aggregateTVShowSet(tvShowSet, getTopRatedTVShowPageN(i));
        }
        return tvShowSet;
    }
 }
 