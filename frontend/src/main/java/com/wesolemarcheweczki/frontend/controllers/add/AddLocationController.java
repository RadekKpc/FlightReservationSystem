package com.wesolemarcheweczki.frontend.controllers.add;

import com.wesolemarcheweczki.frontend.model.Location;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddLocationController extends GenericAddController {
    private final Location location = new Location();
    @FXML
    private TextField airportIdTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField countryTextField;

    public void addLocation() throws IOException, InterruptedException {
        add(location, "/location");
    }

    @Override
    boolean allFieldsFilled() {
        return !location.getCity().isEmpty() && !location.getCountry().isEmpty() && !location.getAirportId().isEmpty();
    }

    @Override
    void clearFields() {
        airportIdTextField.setText("");
        cityTextField.setText("");
        countryTextField.setText("");
    }

    @Override
    boolean checkFields() {
        return true;
    }

    @Override
    void updateModel() {
        String airportId = airportIdTextField.getText();
        String city = cityTextField.getText();
        String country = countryTextField.getText();
        this.location.setAirportId(airportId);
        this.location.setCity(city);
        this.location.setCountry(country);
    }
}
