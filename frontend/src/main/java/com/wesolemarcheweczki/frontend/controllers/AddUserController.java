package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Client;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.regex.Pattern;

public class AddUserController {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    Text errorText;

    private Client client = new Client();

    private final RestClient restClient = new RestClient();

    private static String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @FXML
    private void addUser() throws IOException, InterruptedException {
        // get values from text labels
        this.updateModel();
        errorText.setText("");
        if (!Pattern.matches(AddUserController.emailRegex, this.client.getEmail())){
            // email provided doesnt match regex
            wrongEmailHandle();
            System.out.println("Wrong email!");
        } else if (!this.client.getFirstName().isEmpty() && !this.client.getLastName().isEmpty()) {
            if (restClient.postObject(this.client, "/client")) { // user successfully added to database
                firstNameTextField.setText("");
                lastNameTextField.setText("");
                emailTextField.setText("");
                passwordField.setText("");
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

    private void updateModel() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String pwd = passwordField.getText();
        this.client.setEmail(email);
        this.client.setFirstName(firstName);
        this.client.setLastName(lastName);
        this.client.setPassword(pwd);
    }
}
