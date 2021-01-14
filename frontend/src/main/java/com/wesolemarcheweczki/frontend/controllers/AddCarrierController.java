package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddCarrierController implements Initializable  {

    private final RestClient restClient = new RestClient();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final ObservableList<Carrier> listOfCarriers = FXCollections.observableArrayList();
    public TableColumn<Carrier, Carrier> carrierColumn;
    public Button addCarrierButton;
    public TextField carrierName;
    @FXML
    private TableView<Carrier> dataTable;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        reset();
    }

    public void reset() {
        initColumns();
        Task<List<Carrier>> getCarriers = restClient.createGetTask("/carrier", Carrier.class);

        getCarriers.setOnSucceeded(event -> {
            var carriers = getCarriers.getValue();
            listOfCarriers.addAll(carriers);
        });

        executorService.submit(getCarriers);
    }

    private void initColumns() {
        addCarrierButton.setOnAction(this::addCarrier);
        carrierColumn.setCellValueFactory(cellData -> cellData.getValue().carrierProperty());
        carrierColumn.setCellFactory(ComboBoxTableCell.forTableColumn(listOfCarriers));
        dataTable.setItems(listOfCarriers);

    }

    private void addCarrier(ActionEvent actionEvent) {
        String nameOfCarrier = carrierName.getText();
        Carrier c = new Carrier(nameOfCarrier);
        try {
            restClient.postObject(c, "/carrier");
            listOfCarriers.add(c);
            carrierName.setText("");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
