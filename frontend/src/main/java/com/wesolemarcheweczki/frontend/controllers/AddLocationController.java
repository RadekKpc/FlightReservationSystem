package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Location;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class AddLocationController {
    @FXML
    private TextField airportIdTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField countryTextField;
    @FXML
    private Text errorText;

    private final RestClient restClient = new RestClient();

    private Location location = new Location();

    public void addLocation() throws IOException, InterruptedException {
        updateModel();
        errorText.setText("");
        if (!location.getCity().isEmpty() && !location.getCountry().isEmpty() && !location.getAirportId().isEmpty()) {
            if (restClient.postObject(location, "/location")) { // location successfully added to database
                airportIdTextField.setText("");
                cityTextField.setText("");
                countryTextField.setText("");
                successfullyAddedLocation();
            } else {
                couldntAddLocation();
            }
        }
    }

    private void couldntAddLocation() { // show error message that the location could not get added to database
        errorText.setText("Could not add location to database!");
        errorText.setStyle("-fx-fill: red;");
    }

    private void successfullyAddedLocation() { // show information that the location was successfully added to database
        errorText.setText("Added location to database!");
        errorText.setStyle("-fx-fill: green;");
    }

    private void updateModel() {
        String airportId = airportIdTextField.getText();
        String city = cityTextField.getText();
        String country = countryTextField.getText();
        this.location.setAirportId(airportId);
        this.location.setCity(city);
        this.location.setCountry(country);
    }
}
