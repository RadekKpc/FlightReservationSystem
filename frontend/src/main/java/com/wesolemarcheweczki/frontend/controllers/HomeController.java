package com.wesolemarcheweczki.frontend.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HomeController {

    @FXML
    private Pane outsidePane;

    @FXML
    private void handleAddUser() throws IOException {
        // change the view of the scene to include AddUser
        outsidePane.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/views/AddUser.fxml"));
        outsidePane.getChildren().add(newLoadedPane);
    }

    @FXML
    private void handleAddCarrier() throws IOException {
        // change the view of the scene to include AddUser
        outsidePane.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/views/AddClient.fxml"));
        outsidePane.getChildren().add(newLoadedPane);
    }

    @FXML
    private void exit() {
        System.exit(0);
    }
}
