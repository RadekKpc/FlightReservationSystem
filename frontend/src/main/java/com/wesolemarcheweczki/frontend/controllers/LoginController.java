package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.Main;
import com.wesolemarcheweczki.frontend.model.Client;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.regex.Pattern;

public class LoginController {

    @FXML
    private Text errorText;
    @FXML
    private PasswordField registerConfirmPassword;
    @FXML
    private TextField registerEmail;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private TextField registerLastName;
    @FXML
    private TextField registerFirstName;
    @FXML
    private PasswordField loginPassword;
    @FXML
    private TextField loginEmail;

    private final RestClient restClient = new RestClient();

    private static String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private Client client = new Client();

    @FXML
    private void login() throws IOException, InterruptedException {
        String email = loginEmail.getText();
        String pwd = loginPassword.getText();
        client.setEmail(email);
        if (restClient.authorizeLogin(email,pwd)) {
            loadHomePage(client.getEmail());
        }
    }

    @FXML
    private void register() throws IOException, InterruptedException {
        updateModelRegister();
        String pass1 = registerPassword.getText();
        String pass2 = registerConfirmPassword.getText();
        if (!pass1.equals(pass2)) {
            passwordsDontMatchHandle();
            return;
        }
        if (!Pattern.matches(emailRegex, this.client.getEmail())) {
            wrongEmailHandle();
            return;
        }
        if (restClient.postObject(this.client, "/client")) { // user successfully added to database
            registerPassword.setText("");
            registerConfirmPassword.setText("");
            registerLastName.setText("");
            registerFirstName.setText("");
            registerEmail.setText("");
            loadHomePage(client.getEmail());
        } else {
            couldntRegister();
        }

    }

    private void passwordsDontMatchHandle() { // if passwords don't match in register
        errorText.setText("Passwords don't match!");
        errorText.setStyle("-fx-fill: red;");
    }

    private void wrongEmailHandle() { // show error message that the email provided is wrong
        errorText.setText("Wrong email!");
        errorText.setStyle("-fx-fill: red;");
    }

    private void updateModelRegister() {
        String firstName = registerFirstName.getText();
        String lastName = registerLastName.getText();
        String email = registerEmail.getText();
        String pwd = registerPassword.getText();
        this.client.setEmail(email);
        this.client.setFirstName(firstName);
        this.client.setLastName(lastName);
        this.client.setPassword(pwd);
    }

    private void couldntRegister() { // show error message that the user could not get added to database
        errorText.setText("Could not register!");
        errorText.setStyle("-fx-fill: red;");
    }

    private void loadHomePage(String email) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Main.setScene(scene);
        HomeController hc = loader.getController();
        hc.setCurrentlyLoggedInClient(email);
    }
}
