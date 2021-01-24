package com.wesolemarcheweczki.frontend.controllers.add;

import com.wesolemarcheweczki.frontend.model.Client;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Pattern;

public class AddUserController extends GenericAddController {

    private static final String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final RestClient restClient = new RestClient();
    private final Client client = new Client();
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleField;

    @Override
    boolean allFieldsFilled() {
        return !this.client.getFirstName().isEmpty() && !this.client.getLastName().isEmpty();
    }

    @Override
    boolean checkFields() {
        boolean correct = Pattern.matches(AddUserController.emailRegex, this.client.getEmail());

        if (!correct) {// email provided doesnt match regex
            wrongEmailHandle();
            System.out.println("Wrong email!");
        }

        return correct;
    }

    void clearFields() {
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        emailTextField.setText("");
        passwordField.setText("");
    }

    @Override
    void updateModel() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String pwd = passwordField.getText();
        String role = "ROLE_" + roleField.getValue();

        this.client.setEmail(email);
        this.client.setFirstName(firstName);
        this.client.setLastName(lastName);
        this.client.setPasswordWithoutEncoding(pwd);
        this.client.setRole(role);
    }

    @FXML
    private void addUser() throws IOException, InterruptedException {
        add(client, "/client/admin");
    }
}
