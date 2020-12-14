package com.wesolemarcheweczki.frontend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class FlightsController {

    @FXML
    private VBox flightContainer;

    private final RestClient restClient = new RestClient();

    public void loadFlights() throws IOException, InterruptedException {
        String response = restClient.getObject("/flight");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Flight> valuesList = mapper.readValue(response, new TypeReference<>(){});
        System.out.println(valuesList);
        for(Flight v : valuesList) {
            //System.out.println(v);
            addFlightToList(v);
        }
    }

    public void addFlightToList(Flight f) {

    }
}
