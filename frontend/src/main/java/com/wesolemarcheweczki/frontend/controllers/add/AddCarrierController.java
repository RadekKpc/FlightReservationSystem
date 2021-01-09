package com.wesolemarcheweczki.frontend.controllers.add;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddCarrierController extends GenericAddController {
    @FXML
    private TextField carrierName;


    private final RestClient client = new RestClient();

    private final Carrier carrier = new Carrier();

    @Override
    boolean allFieldsFilled() {
        return !carrier.getName().isEmpty();
    }

    void updateModel() {
        String nameOfCarrier = carrierName.getText();
        this.carrier.setName(nameOfCarrier);
    }

    @Override
    void clearFields() {

    }

    @Override
    boolean checkFields() {
        return true;
    }

    @FXML
    private void addCarrier() throws IOException, InterruptedException {
        add(carrier, "/carrier");
    }
}
