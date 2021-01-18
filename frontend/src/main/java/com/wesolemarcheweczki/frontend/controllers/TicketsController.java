package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.*;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
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
        lastNameColumn.setCellValueFactory(tick -> tick.getValue().lastNameProperty());
        fromColumn.setCellValueFactory(tick -> tick.getValue().fromProperty());
        toColumn.setCellValueFactory(tick -> tick.getValue().toProperty());
        departureColumn.setCellValueFactory(tick -> tick.getValue().departureProperty());
        arrivalColumn.setCellValueFactory(tick -> tick.getValue().arrivalProperty());
        seatColumn.setCellValueFactory(tick -> tick.getValue().seatProperty());
        costColumn.setCellValueFactory(tick -> tick.getValue().costProperty());

        dataTable.setItems(listOfTicket);

    }
}
