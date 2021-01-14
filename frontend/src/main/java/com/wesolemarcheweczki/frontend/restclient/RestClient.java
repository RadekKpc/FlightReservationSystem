package com.wesolemarcheweczki.frontend.restclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wesolemarcheweczki.frontend.model.Client;
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


public class RestClient {
    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final String url = "http://localhost:8080/api"; //TODO: as property
    private static Client loggedClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public RestClient() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public static Client getLoggedClient() {
        return loggedClient;
    }

    public static void setLoggedClient(Client client) {
        RestClient.loggedClient = client;
    }

    public String getString(String email, String pwd, String endpoint) throws IOException, InterruptedException {
        HttpResponse<String> response = sendGetRequest(email, pwd, endpoint);

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }

    public boolean check(String endpoint, Object body) throws IOException, InterruptedException {
        var parsedObject = mapper.writeValueAsString(body);
        System.out.println(parsedObject);

        HttpRequest request = httpRequestWithAuth(endpoint, parsedObject, "GET");

        var result = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();

        System.out.println("RESULT STRING: " + result);
        System.out.println("RESULT VALUE: " + Boolean.valueOf(result));
        return Boolean.valueOf(result);
    }

    public boolean putObject(Object obj, String endpoint) throws IOException, InterruptedException {
        return makeRequest(obj, endpoint, "PUT", true);
    }

    public boolean postObject(Object obj, String endpoint) throws IOException, InterruptedException {
        return makeRequest(obj, endpoint, "POST", true);
    }

    public boolean deleteObject(String endpoint, Object object) throws IOException, InterruptedException {
        return makeRequest(object, endpoint, "DELETE", true);
    }

    public boolean postObjectWithoutAuth(Object obj, String endpoint) throws IOException, InterruptedException {
        var parsedObject = mapper.writeValueAsString(obj);
        System.out.println("passedObject: " + parsedObject);

        HttpRequest request = httpRequestNoAuth(endpoint, parsedObject, "POST");

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        return response.statusCode() == 200;
    }

    public boolean authorizeLogin(String login, String pwd) throws IOException, InterruptedException {
        return sendGetRequest(login, pwd, "/check").statusCode() == 200;
    }

    public <T> Task<List<T>> createGetTask(String endpoint, Class<T> className) {
        return new Task<>() {
            @Override
            protected List<T> call() throws Exception {
                String response = getString(AuthManager.email, AuthManager.pwd, endpoint);
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


    private boolean makeRequest(Object obj, String endpoint, String put, boolean useAuth) throws IOException, InterruptedException {
        var parsedObject = mapper.writeValueAsString(obj);
        System.out.println(parsedObject);

        HttpRequest request = httpRequest(endpoint, put, useAuth, parsedObject);

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        return response.statusCode() == 200;
    }

    public <T> T makeRequestWithBodyAndReturn(Object obj, String endpoint, String put, boolean useAuth, Class<T> tClass) throws IOException, InterruptedException {
        var parsedObject = mapper.writeValueAsString(obj);
        System.out.println(parsedObject);

        HttpRequest request = httpRequest(endpoint, put, useAuth, parsedObject);

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        TypeFactory typeFactory = mapper.getTypeFactory();
        //Type t = typeFactory.constructType(tClass);
        return mapper.readValue(response.body(), tClass);
    }

    private HttpRequest httpRequest(String endpoint, String put, boolean useAuth, String parsedObject) {
        HttpRequest request;
        if (useAuth) {
            request = httpRequestWithAuth(endpoint, parsedObject, put);
        } else {
            request = httpRequestNoAuth(endpoint, parsedObject, put);
        }
        return request;
    }

    private HttpResponse<String> sendGetRequest(String login, String pwd, String endpoint) throws IOException, InterruptedException {
        String auth = login + ":" + pwd;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        HttpRequest request = getRequestAuthEmail(login, endpoint, authHeader);

        return httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest getRequestAuthEmail(String login, String endpoint, String authHeader) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .GET()
                .header("email", login)
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpRequest httpRequestNoAuth(String endpoint, String parsedObject, String type) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .method(type, HttpRequest.BodyPublishers.ofString(parsedObject))
                .header("Content-Type", "application/json")
                .build();
    }

    private HttpRequest httpRequestWithAuth(String endpoint, String parsedObject, String type) {
        String authHeader = getAuthHeader();
        return HttpRequest.newBuilder()
                .uri(URI.create(url + endpoint))
                .method(type, HttpRequest.BodyPublishers.ofString(parsedObject))
                .header("Authorization", authHeader)
                .header("Content-Type", "application/json")
                .build();
    }

    private String getAuthHeader() {
        String email = RestClient.loggedClient.getEmail();
        String pwd = RestClient.loggedClient.getPassword();
        String auth = email + ":" + pwd;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }
}
