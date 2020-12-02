package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class AddCarrierController {
    @FXML
    private TextField carrierName;
    @FXML
    private Text errorText;

    private RestClient client = new RestClient();

    @FXML
    private void addCarrier() throws IOException, InterruptedException {
        String carrier = carrierName.getText();
        errorText.setText("");
        // TODO add to database
        if (!carrier.isEmpty() && client.postObject(new Carrier(carrier), "/carrier"))
            addedCarrier();
        else couldntAddCarrier();
    }

    private void couldntAddCarrier() { // show error message that the user could not get added to database
        errorText.setText("Could not add carrier to database!");
        errorText.setStyle("-fx-fill: red;");
    }

    private void addedCarrier() {
        errorText.setText("Carrier added to database!");
        errorText.setStyle("-fx-fill: green;");
    }
}
