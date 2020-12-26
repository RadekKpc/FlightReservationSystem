package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.Main;
import com.wesolemarcheweczki.frontend.model.Client;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import com.wesolemarcheweczki.frontend.util.AuthManager;
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
    private Text loginErrorText;
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

    private static final String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private final Client client = new Client();

    @FXML
    private void login() throws IOException, InterruptedException {
        String email = loginEmail.getText();
        String pwd = loginPassword.getText();
        if (!Pattern.matches(emailRegex, email)) {
            couldntLogin("Wrong email!");
            return;
        }
        if (pwd.isEmpty()) {
            couldntLogin("Password cannot be empty");
            return;
        }
        client.setEmail(email);
        client.setPasswordWithoutEncoding(pwd);
        if (restClient.authorizeLogin(email,pwd)) {
            loadHomePage(client.getEmail(), client.getPassword());
            String role = restClient.getObject(client.getEmail(),client.getPassword(),"/client/role");
            AuthManager.setRole(role);
            AuthManager.setEmail(client.getEmail());
            AuthManager.setPwd(client.getPassword());
            System.out.println(AuthManager.role);
        } else {
            couldntLogin("Not authorized!");
        }
    }

    @FXML
    private void register() throws IOException, InterruptedException {
        updateModelRegister();
        String pass1 = registerPassword.getText();
        String pass2 = registerConfirmPassword.getText();
        if (pass1.isEmpty()) {
            couldntRegister("Password cannot be empty");
            return;
        }
        if (!pass1.equals(pass2)) {
            couldntRegister("Passwords don't match");
            return;
        }
        if (!Pattern.matches(emailRegex, this.client.getEmail())) {
            couldntRegister("Wrong email!");
            return;
        }
        if (restClient.postObjectWithoutAuth(this.client, "/client")) { // user successfully added to database
            registerPassword.setText("");
            registerConfirmPassword.setText("");
            registerLastName.setText("");
            registerFirstName.setText("");
            registerEmail.setText("");
            loadHomePage(client.getEmail(), client.getPassword());
        } else {
            couldntRegister("Couldn't register");
        }

    }
    private void updateModelRegister() {
        String firstName = registerFirstName.getText();
        String lastName = registerLastName.getText();
        String email = registerEmail.getText();
        String pwd = registerPassword.getText();
        this.client.setEmail(email);
        this.client.setFirstName(firstName);
        this.client.setLastName(lastName);
        this.client.setPasswordWithoutEncoding(pwd);
    }

    private void loadHomePage(String email, String pwd) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Main.setScene(scene);
        HomeController hc = loader.getController();
        hc.updateLoggedClient(email, pwd);
    }

    private void couldntLogin(String problem) { // show error message
        loginErrorText.setText(problem);
        loginErrorText.setStyle("-fx-fill: red;");
    }

    private void couldntRegister(String problem) { // show error message that the user could not get added to database
        errorText.setText(problem);
        errorText.setStyle("-fx-fill: red;");
    }
}
