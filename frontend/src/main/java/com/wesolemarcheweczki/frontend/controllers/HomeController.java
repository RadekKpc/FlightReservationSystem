package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.Main;
import com.wesolemarcheweczki.frontend.model.Client;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeController {

    @FXML
    public FontAwesomeIconView viewIcon;
    @FXML
    public Label viewTitle;
    public Label loggedInLabel;
    @FXML
    private Pane viewContent;

    private final Client client = new Client();

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void minimalize() {
        Main.getPrimaryStage().setIconified(true);
    }

    @FXML
    public void handleTickets(ActionEvent actionEvent) {
    }

    @FXML
    public void handleLocations(ActionEvent actionEvent) {
    }

    @FXML
    public void handleCustomers(ActionEvent actionEvent) throws IOException {
        loadPane("/views/AddUser.fxml");
    }

    @FXML
    public void handleFlights(ActionEvent actionEvent) throws IOException, InterruptedException {
        loadPane("/views/Flights.fxml");
    }

    @FXML
    public void handleCarriers(ActionEvent actionEvent) throws IOException {
        loadPane("/views/AddCarrier.fxml");
    }

    @FXML
    public void handleOverview(ActionEvent actionEvent) {
    }

    public void logout() {
        RestClient.setLoggedClient(new Client());
        exit();
    }

    public FXMLLoader loadPane(String resource) throws IOException { // load pane on the right side from fxml view
        viewContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();
        viewContent.getChildren().add(root);
        return loader;
    }

    public void updateLoggedClient(String email, String pwd){
        client.setEmail(email);
        client.setPasswordWithoutEncoding(pwd);
        RestClient.setLoggedClient(client);
        loggedInLabel.setText("Logged in as " + email);
    }
}
