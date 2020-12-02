package com.wesolemarcheweczki.frontend.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesolemarcheweczki.frontend.model.Client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RestClient {
    private String url = "http://localhost:8080/api";
    private ObjectMapper mapper = new ObjectMapper();
    public boolean postObject(Object obj, String endpoint ) throws IOException, InterruptedException {
        var parsedObject = mapper.writeValueAsString(obj);

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(parsedObject))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());


        return response.statusCode() == 200;
    }
}
