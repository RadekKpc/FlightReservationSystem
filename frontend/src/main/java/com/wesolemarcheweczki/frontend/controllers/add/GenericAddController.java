package com.wesolemarcheweczki.frontend.controllers.add;

import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;

public abstract class GenericAddController {
    private final RestClient restClient = new RestClient();
    @FXML
    Text errorText;

    abstract boolean allFieldsFilled();

    abstract void updateModel();

    abstract void clearFields();

    abstract boolean checkFields();


    void add(Object object, String endpoint) throws IOException, InterruptedException {
        updateModel();
        errorText.setText("");
        if (allFieldsFilled() && checkFields()) {
            boolean postedSuccessfully = restClient.postObject(object, endpoint);
            updateViewAfterPosting(postedSuccessfully);
        }
    }

    void updateViewAfterPosting(boolean postedSuccessfully) {
        if (postedSuccessfully) {
            clearFields();
            successfullyAddedLocation();
        } else {
            couldntAddLocation();
        }
    }

    void couldntAddLocation() { // show error message that the location could not get added to database
        setErrorText("Could not add location to database!", "-fx-fill: red;");
    }

    void successfullyAddedLocation() { // show information that the location was successfully added to database
        setErrorText("Added location to database!", "-fx-fill: green;");
    }

    void wrongEmailHandle() { // show error message that the email provided is wrong
        setErrorText("Wrong email!", "-fx-fill: red;");
    }

    private void setErrorText(String s, String s2) {
        errorText.setText(s);
        errorText.setStyle(s2);
    }
}
