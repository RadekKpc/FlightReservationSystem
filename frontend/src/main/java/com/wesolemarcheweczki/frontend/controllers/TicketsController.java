package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.*;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import com.wesolemarcheweczki.frontend.util.AuthManager;
import com.wesolemarcheweczki.frontend.util.FlightRecommendations;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketsController implements Initializable {

    private final RestClient restClient = new RestClient();
    private final ObservableList<Ticket> listOfTicket = FXCollections.observableArrayList();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final String endpoint = String.format("/ticket/email/%s", RestClient.getLoggedClient().getEmail());

    @FXML
    private TableView<Ticket> dataTable;
    @FXML
    private TableColumn<Ticket, String> nameColumn;
    @FXML
    private TableColumn<Ticket, String> lastNameColumn;
    @FXML
    private TableColumn<Ticket, String> fromColumn;
    @FXML
    private TableColumn<Ticket, String> toColumn;
    @FXML
    private TableColumn<Ticket, LocalDateTime> departureColumn;
    @FXML
    private TableColumn<Ticket, LocalDateTime> arrivalColumn;
    @FXML
    private TableColumn<Ticket, String> seatColumn;
    @FXML
    private TableColumn<Ticket, String> costColumn;



    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        reset();

    }

    public void reset() {
        initColumns();
        Task<List<Ticket>> getTickets = restClient.createGetTask(endpoint, Ticket.class);

        getTickets.setOnSucceeded(event -> {
            var tickets = getTickets.getValue();
            listOfTicket.addAll(tickets);
        });

        executorService.submit(getTickets);
    }

    private void initColumns() {

        nameColumn.setCellValueFactory(tick -> tick.getValue().nameProperty());
        lastNameColumn.setCellValueFactory(tick -> tick.getValue().lastNamePropertyProperty());
        fromColumn.setCellValueFactory(tick -> tick.getValue().fromPropertyProperty());
        toColumn.setCellValueFactory(tick -> tick.getValue().toPropertyProperty());
        departureColumn.setCellValueFactory(tick -> tick.getValue().departurePropertyProperty());
        arrivalColumn.setCellValueFactory(tick -> tick.getValue().arrivalPropertyProperty());
        seatColumn.setCellValueFactory(tick -> tick.getValue().seatPropertyProperty());
        costColumn.setCellValueFactory(tick -> tick.getValue().costPropertyProperty());

        dataTable.setItems(listOfTicket);

    }
}
