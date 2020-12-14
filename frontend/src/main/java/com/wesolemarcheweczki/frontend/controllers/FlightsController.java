package com.wesolemarcheweczki.frontend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FlightsController implements Initializable {

    @FXML
    private VBox flightContainer;

    private final RestClient restClient = new RestClient();

    public void loadFlights() throws IOException, InterruptedException {
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
        sfc.setData(f);
        flightContainer.getChildren().add(root);
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadFlights();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
