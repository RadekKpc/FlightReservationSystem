package com.wesolemarcheweczki.frontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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
            // add user to database TODO
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
}
