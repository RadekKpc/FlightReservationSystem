package com.wesolemarcheweczki.frontend.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wesolemarcheweczki.frontend.model.Flight;
import javafx.concurrent.Task;
import org.apache.commons.codec.binary.Base64;
import org.yaml.snakeyaml.events.CollectionStartEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


public class RestClient<T> {
    private static HttpClient httpClient = HttpClient.newHttpClient();
    private String url = "http://localhost:8080/api";
    private ObjectMapper mapper = new ObjectMapper();

    private String getAuthHeader(){
        String auth = "client_email1@sample.com" + ":" + "client1pwd";
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }


    public boolean putObject(Object obj, String endpoint) throws IOException, InterruptedException {
        String authHeader = getAuthHeader();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

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
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

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


    public String getObject(String endpoint) throws IOException, InterruptedException {
        String authHeader = getAuthHeader();

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

    public Task<List<T>> createGetTask(String endpoint, Class<T> className){
        return new Task<List<T>>() {

            @Override
            protected List<T> call() throws Exception {
                String response = getObject(endpoint);
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

}
