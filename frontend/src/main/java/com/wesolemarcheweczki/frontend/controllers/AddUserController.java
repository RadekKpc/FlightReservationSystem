package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.regex.Pattern;

public class AddUserController {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    Text errorText;

    @FXML
    private void addUser() {
        // get values from text labels
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        errorText.setText("");
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!Pattern.matches(emailRegex, email)){
            // email provided doesnt match regex
            wrongEmailHandle();
            System.out.println("Wrong email!");
        } else if (!firstName.isEmpty() && !lastName.isEmpty()) {
            //  TODO add user to database
            if (true) { // user successfully added to database
                firstNameTextField.setText("");
                lastNameTextField.setText("");
                emailTextField.setText("");
                successfullyAddedUser();
            } else {
                couldntAddUser();
            }
        }
    }

    private void wrongEmailHandle() { // show error message that the email provided is wrong
        errorText.setText("Wrong email!");
        errorText.setStyle("-fx-fill: red;");
    }

    private void couldntAddUser() { // show error message that the user could not get added to database
        errorText.setText("Could not add user to database!");
        errorText.setStyle("-fx-fill: red;");
    }

    private void successfullyAddedUser() { // show information that the user was successfully added to database
        errorText.setText("Added user to database!");
        errorText.setStyle("-fx-fill: green;");
    }

    public static boolean postClient(String firstName, String lastName, String email) {
        Client c = new Client(firstName, lastName, email);

        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:8080/api/client");
        Response clientsResponse = target.request().post(Entity.entity(c, "application/json"));

        System.out.println("HTTP code: " + clientsResponse.getStatus());
        int status = clientsResponse.getStatus();
        clientsResponse.close();
        return status == 200;
    }
}
