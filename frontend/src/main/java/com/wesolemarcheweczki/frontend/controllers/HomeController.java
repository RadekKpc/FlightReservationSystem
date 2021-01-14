package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.Main;
import com.wesolemarcheweczki.frontend.model.Client;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import com.wesolemarcheweczki.frontend.util.Role;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomeController {

    @FXML
    public FontAwesomeIconView viewIcon;
    @FXML
    public Label viewTitle;
    public Label loggedInLabel;

    @FXML
    public VBox homeVBox;
    @FXML
    public Button btnCarriers;
    @FXML
    public Button btnCustomers;
    @FXML
    public Button btnLocations;
    @FXML
    public Button btnFlights;
    @FXML
    public Button btnTickets;
    @FXML
    public Button btnCarrierStats;

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
    public void handleTickets(ActionEvent actionEvent) throws IOException {
        loadPane("/views/Tickets.fxml");
    }

    @FXML
    public void handleLocations(ActionEvent actionEvent) throws IOException  {
        loadPane("/views/AddLocation.fxml");
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

    public void logout() throws IOException {
        RestClient.setLoggedClient(new Client());
//        exit();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginRegister.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Main.setScene(scene);
    }

    public FXMLLoader loadPane(String resource) throws IOException { // load pane on the right side from fxml view
        viewContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
        Parent root = loader.load();
        viewContent.getChildren().add(root);
        return loader;
    }

    public void updateLoggedClient(String email, String pwd, Role role) {
        client.setEmail(email);
        client.setPasswordWithoutEncoding(pwd);
        client.setRole(Role.asText(role));
        RestClient.setLoggedClient(client);
        removeNotPermittedButtons(role);
        loggedInLabel.setText("Logged in as " + email);
    }

    private void removeNotPermittedButtons(Role role) {
        if (role == Role.USER) {
            removeAdminButtons();
        }
        if (role == Role.ADMIN) {
            homeVBox.getChildren().remove(btnTickets);
        }
    }

    private void removeAdminButtons() {
        homeVBox.getChildren().remove(btnCarriers);
        homeVBox.getChildren().remove(btnLocations);
        homeVBox.getChildren().remove(btnCustomers);
        homeVBox.getChildren().remove(btnCarrierStats);
    }

    public void handleCarrierStats(ActionEvent actionEvent) throws IOException {
        loadPane("/views/CarrierStats.fxml");
    }
}
