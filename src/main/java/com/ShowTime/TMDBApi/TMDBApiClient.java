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
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
 /**
  *
  * @author anthony
  */
 public class TMDBApiClient {
 
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

     public static JSONArray handleApiResponse(String response){
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray results = jsonResponse.optJSONArray("results");
        return results;
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
 
     public static void main(String[] args) {
        String response = makeRequest("movie/top_rated?language=en-US&page=1");
        //String response = makeRequest("movie/603");
       System.out.println(handleMovieList(handleApiResponse(response)));
 }
 }
 