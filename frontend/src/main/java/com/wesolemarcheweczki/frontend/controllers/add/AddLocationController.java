package com.wesolemarcheweczki.frontend.controllers.add;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.model.Location;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddLocationController implements Initializable {
    private final RestClient restClient = new RestClient();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Task<List<Location>> getLocation = restClient.createGetTask("/location", Location.class);
    private final Location location = new Location();
    public TextField cityName;
    public TextField countryName;
    public Button addLocationButton;
    public TableColumn<Location,String> cityNameTableColumn;
    public TableColumn<Location,String> countryNameTableColumn;
    private final ObservableList<Location> listOfLocations = FXCollections.observableArrayList();
    public TextField airportID;
    public TableColumn<Location,String> airportNameTableColumn;

    @FXML
    private TableView<Location> dataTable;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        reset();
    }

    public void reset() {
        initColumns();
        getLocation.setOnSucceeded(event -> {
            var locations = getLocation.getValue();
            listOfLocations.addAll(locations);
        });
        executorService.submit(getLocation);
    }

    private void initColumns() {
        addLocationButton.setOnAction(this::addLocation);
        cityNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCity()));
        cityNameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        countryNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCountry()));
        countryNameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        airportNameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAirportId()));
        airportNameTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        dataTable.setItems(listOfLocations);

    }

    private void addLocation(ActionEvent actionEvent) {
        String city = cityName.getText();
        String country = countryName.getText();
        String airport = airportID.getText();
        Location l = new Location(airport,city,country);
        try {
            restClient.postObject(l, "/location");
            listOfLocations.add(l);
            cityName.setText("");
            countryName.setText("");
            airportID.setText("");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
