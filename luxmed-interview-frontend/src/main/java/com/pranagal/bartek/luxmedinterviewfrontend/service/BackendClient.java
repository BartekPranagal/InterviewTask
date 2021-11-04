package com.pranagal.bartek.luxmedinterviewfrontend.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class BackendClient {


    HttpClient httpClient;

    public BackendClient() {
        this.httpClient = HttpClient.newBuilder().build();
    }

    public HttpResponse<String> searchUsers(String query) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users".concat(Optional.ofNullable(query).map(x -> "?query=" + x).orElse(""))))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getShipmentsForUser(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users/".concat(id.toString()).concat("/shipments")))
                .GET()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> addNewUser(String newUserJson) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users"))
                .POST(HttpRequest.BodyPublishers.ofString(newUserJson))
                .header("Content-Type", "application/json" ) //potrzebne zeby spring zrozumial co mu wysylamy
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> updateUser(Long userId, String editedUserJson) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users/" + userId.toString()))
                .PUT(HttpRequest.BodyPublishers.ofString(editedUserJson))
                .header("Content-Type", "application/json" ) //potrzebne zeby spring zrozumial co mu wysylamy
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> deleteUser(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/users/" + id.toString()))
                .DELETE()
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
