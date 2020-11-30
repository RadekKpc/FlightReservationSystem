package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.Main;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

    @FXML
    private void handleAddUser() throws IOException {
        // change the view of the scene to include AddUser
        viewContent.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/views/AddUser.fxml"));
        viewContent.getChildren().add(newLoadedPane);
        viewIcon.setIcon(FontAwesomeIcon.USERS);
        viewTitle.setText("Add User");
    }

    @FXML
    private void handleAddCarrier() throws IOException {
        // change the view of the scene to include AddUser
        viewContent.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(getClass().getResource("/views/AddCarrier.fxml"));
        viewContent.getChildren().add(newLoadedPane);
        viewIcon.setIcon(FontAwesomeIcon.PLANE);
        viewTitle.setText("Add Carrier");
    }

    @FXML
    private void exit() {
        System.exit(0);
    }

    @FXML
    private void minimalize() {
        Main.getPrimaryStage().setIconified(true);
    }
}
