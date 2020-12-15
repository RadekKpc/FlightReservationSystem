package com.wesolemarcheweczki.frontend.restclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesolemarcheweczki.frontend.model.Flight;
import org.apache.commons.codec.binary.Base64;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class RestClient {
    private static HttpClient httpClient = HttpClient.newHttpClient();
    private String url = "http://localhost:8080/api";
    private ObjectMapper mapper = new ObjectMapper();

    public boolean postObject(Object obj, String endpoint ) throws IOException, InterruptedException {
        var parsedObject = mapper.writeValueAsString(obj);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(parsedObject))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());


        return response.statusCode() == 200;
    }


    public String getObject(String endpoint) throws IOException, InterruptedException {
        String auth = "client_email1@sample.com" + ":" + "client1pwd";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .GET()
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }

    public boolean authorizeLogin(String login, String pwd) throws IOException, InterruptedException {
        String auth = login + ":" + pwd;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + "/check"))
                .GET()
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        return response.statusCode() == 200;
    }

}
