/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ShowTime.model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author anthony
 */
public class TestTMDB {

    private static final String API_KEY = "xxx";

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.themoviedb.org/3/authentication"))
            .GET()
            .header("accept", "application/json")
            .header("Authorization",API_KEY)
            .build();
        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.statusCode());
            //System.out.println(response.body());
            JSONObject jsonObject = new JSONObject(response.body());
            //System.out.println(jsonObject);
            /*
             * Fields to retrieve
             * title
             * genres -> Filter
             * overview
             */
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < 1; i++) {
                System.out.println(results.get(i));
            }
        } catch (Exception e) {
        }
}
}
