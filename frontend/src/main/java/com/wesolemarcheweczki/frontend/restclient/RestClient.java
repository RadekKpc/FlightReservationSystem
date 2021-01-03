package com.wesolemarcheweczki.frontend.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wesolemarcheweczki.frontend.model.Client;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.util.AuthManager;
import javafx.concurrent.Task;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class RestClient<T> {
    private static HttpClient httpClient = HttpClient.newHttpClient();
    private static Client loggedClient;
    private String url = "http://localhost:8080/api";
    private ObjectMapper mapper = new ObjectMapper();

    public static Client getLoggedClient() {
        return loggedClient;
    }


    private String getAuthHeader(){
        String email = RestClient.loggedClient.getEmail();
        String pwd = RestClient.loggedClient.getPassword();
        String auth = email + ":" + pwd;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }


    public boolean putObject(Object obj, String endpoint) throws IOException, InterruptedException {
        String authHeader = getAuthHeader();

        var parsedObject = mapper.writeValueAsString(obj);
        System.out.println(parsedObject);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .PUT(HttpRequest.BodyPublishers.ofString(parsedObject))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        return response.statusCode() == 200;
    }

    public boolean postObject(Object obj, String endpoint ) throws IOException, InterruptedException {
        String authHeader = getAuthHeader();

        var parsedObject = mapper.writeValueAsString(obj);
        System.out.println(parsedObject);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(parsedObject))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        return response.statusCode() == 200;
    }

    public boolean postObjectWithoutAuth(Object obj, String endpoint ) throws IOException, InterruptedException {
        var parsedObject = mapper.writeValueAsString(obj);
        System.out.println("passedObject: " + parsedObject);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .POST(HttpRequest.BodyPublishers.ofString(parsedObject))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        return response.statusCode() == 200;
    }

    public String getObject(String email, String pwd, String endpoint) throws IOException, InterruptedException {
        HttpResponse<String> response = getResponse(email, pwd, endpoint);

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }

    public boolean authorizeLogin(String login, String pwd) throws IOException, InterruptedException {
        return getResponse(login, pwd, "/check").statusCode() == 200;
    }

    public HttpResponse<String> getResponse(String login, String pwd, String endpoint) throws IOException, InterruptedException {
        String auth = login + ":" + pwd;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .GET()
                .header("email",login)
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();

        return httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());
    }

    public Task<List<T>> createGetTask(String endpoint, Class<T> className){
        return new Task<List<T>>() {

            @Override
            protected List<T> call() throws Exception {
                String response = getObject(AuthManager.email, AuthManager.pwd, endpoint);
                System.out.println(response);
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                TypeFactory typeFactory = mapper.getTypeFactory();
                CollectionType type = typeFactory.constructCollectionType(List.class, className);
                List<T> valueList = mapper.readValue(response, type);
                System.out.println(valueList);
                return valueList;
            }
        };
    }

    public static void setLoggedClient(Client client) {
        RestClient.loggedClient = client;
    }

    public boolean deleteObject(String endpoint, Object object) throws IOException, InterruptedException {
        String authHeader = getAuthHeader();
        var parsedObject = mapper.writeValueAsString(object);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(parsedObject))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();
        System.out.println(request);
        var result = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).statusCode() == 200;
        System.out.println(result);
        return result;
    }

    public RestClient(){
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }
}
