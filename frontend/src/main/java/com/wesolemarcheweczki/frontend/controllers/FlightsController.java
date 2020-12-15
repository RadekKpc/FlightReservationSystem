package com.wesolemarcheweczki.frontend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

public class FlightsController {

    @FXML
    private VBox flightContainer;

    private final RestClient restClient = new RestClient();

    public void loadData() throws IOException, InterruptedException {
        flightContainer.getChildren().clear();
        String response = restClient.getObject("/flight");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Flight> valuesList = mapper.readValue(response, new TypeReference<>(){});
        System.out.println(valuesList);
        for(Flight v : valuesList) {
            System.out.println(v);
            addFlightToList(v);
        }
    }

    public void addFlightToList(Flight f) throws IOException { // add flight to VBox list
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item.fxml"));
        Parent root = loader.load();
        SingleFlightController sfc = loader.getController();
        flightContainer.getChildren().add(root);
        sfc.setData(f);
    }
}
