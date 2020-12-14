package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeController {

    @FXML
    public FontAwesomeIconView viewIcon;
    @FXML
    public Label viewTitle;
    @FXML
    private Pane viewContent;

    private FlightsController flightsController = new FlightsController();

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
        flightsController.loadFlights();
    }

    @FXML
    public void handleCarriers(ActionEvent actionEvent) throws IOException {
        loadPane("/views/AddCarrier.fxml");
    }

    @FXML
    public void handleOverview(ActionEvent actionEvent) {
    }

    public void loadPane(String resource) throws IOException {
        // load pane on the right side from fxml view
        viewContent.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource(resource));
        viewContent.getChildren().add(newLoadedPane);

    }
}
