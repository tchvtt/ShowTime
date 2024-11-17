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
 /**
  *
  * @author anthony
  */
 public class TMDBApiClient{
 
     private static final String API_KEY = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyZWJiYWNjNTU3YmNkYWIzZTk2ODE1NDlmNTRmMjg5MiIsIm5iZiI6MTczMTg0ODc5Ni4yMTczMzUsInN1YiI6IjY3MmRkNDYzNWEyMDQ1OTIwNzQxNzE1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.S74Je4ZorwHM6i7Rjj323i7N4wcdpsWEzAMHgiXw8jY";
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

     public static Set<Integer> handleMovieList(JSONArray results){
        JSONObject currentMovie;
        Set<Integer> movieIDSet = new HashSet<>();
        for (int i = 0; i < results.length(); i++) {
            currentMovie = results.getJSONObject(i);
            movieIDSet.add(currentMovie.getInt("id"));
        }
        return movieIDSet;
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

    public static Set<Movie> aggregateMovies(Set<Integer> movieIDs){
        Set<Movie> movieSet = new HashSet<>();
        for (Integer currentMovieID : movieIDs) {
            movieSet.add(handleMovie(currentMovieID));
        }
        return movieSet;
    }

    public static Set<Movie> aggregateMovieSet(Set<Movie> destination, Set<Movie> source){
        for (Movie currentMovie : source) {
            destination.add(currentMovie);
        }
        return destination;
    }

    public static Set<Movie> getTopRatedMoviePageN(int pageN){
        Set<Movie> movieSet = new HashSet<>();
        movieSet = aggregateMovies(handleMovieList(handleApiArrayResponse(makeRequest("movie/top_rated?language=en-US&page="+pageN))));
        return movieSet;
    }
    public static void main(String[] args) {
    Set<Movie> movieSet = new HashSet<>();
    for (int i = 1; i < 6; i++) {
        movieSet = aggregateMovieSet(movieSet, getTopRatedMoviePageN(i));
    }
    System.out.println("Dataset size = " + movieSet.size());
    for (Movie elem : movieSet) {
        System.out.println(elem.getTitle());
    }
 }
 }
 