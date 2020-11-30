package com.wesolemarcheweczki.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class AddCarrierController {
    @FXML
    private TextField carrierName;
    @FXML
    private Text errorText;

    @FXML
    private void addCarrier() {
        String carrier = carrierName.getText();
        errorText.setText("");
        // TODO add to database
        if (!carrier.isEmpty())
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
