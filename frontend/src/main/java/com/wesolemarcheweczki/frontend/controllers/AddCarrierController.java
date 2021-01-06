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

    private Carrier carrier = new Carrier();

    @FXML
    private void addCarrier() throws IOException, InterruptedException {
        this.updateModel();
        errorText.setText("");
        if (!carrier.getName().isEmpty() && client.postObject(this.carrier, "/carrier") == 200)
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

    private void updateModel() {
        String nameOfCarrier = carrierName.getText();
        this.carrier.setName(nameOfCarrier);
    }
}
