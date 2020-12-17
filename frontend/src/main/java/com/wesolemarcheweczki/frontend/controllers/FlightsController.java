package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.model.Location;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FlightsController implements Initializable {

    private ObservableList<Flight> listOfFlights;
    private ObservableList<Carrier> listOfCarriers = FXCollections.observableArrayList();
    private ObservableList<Location> listOfLocations = FXCollections.observableArrayList();

    private TableColumn freeColumn;
    private TableColumn bookedColumn;
    private TableColumn<Flight, String> priceColumn;
    private TableColumn<Flight, String> arrivalColumn;
    private TableColumn<Flight, String> departureColumn;
    private TableColumn<Flight, String> fromColumn;
    private TableColumn <Flight, String> toColumn;
    private TableColumn<Flight, String> carrierColumn;
    private TableView<Flight> dataTable;
    @FXML
    private VBox flightContainer;

    @FXML
    
    
    private final RestClient restClient = new RestClient();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Task<List<Flight>> getFlights = restClient.createGetTask("/flight",Flight.class);

    private final Task<List<Carrier>> getCarriers = restClient.createGetTask("/carrier", Carrier.class);

    private final Task<List<Location>> getLocation = restClient.createGetTask("/location", Location.class);

    private List<Carrier> carrierList;
    private List<Location> locationList;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


    public void addFlightToList(Flight f) throws IOException { // add flight to VBox list
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Item.fxml"));
        Parent root = loader.load();
        SingleFlightController sfc = loader.getController();
        sfc.setData(f);
        flightContainer.getChildren().add(root);
    }

    private void initColumns(){
        dataTable.setEditable(true);
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getBaseCost())));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        arrivalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrival().toString()));
        arrivalColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departureColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getDeparture().toString()));
        departureColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSource().getAirportId()));
        toColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestination().getAirportId()));
        carrierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarrier().getName()));
        priceColumn.setOnEditCommit(this::updateFlightCost);
        carrierColumn.setOnEditCommit(this::updateFlightCarrier);
        arrivalColumn.setOnEditCommit(this::updateArrivalTime);
        departureColumn.setOnEditCommit(this::updateDepartureTime);
        fromColumn.setOnEditCommit(this::updateFrom);
        toColumn.setOnEditCommit(this::updateTo);
    }

    private void updateFlightCost(TableColumn.CellEditEvent<Flight, String> t) {
        int cost = Integer.parseInt(t.getNewValue());
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setBaseCost(cost);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setBaseCost(cost);
        putFlightChange(f);
    }

    private void updateFrom(TableColumn.CellEditEvent<Flight, String> t) {
        Location l = findLocationByName(t.getNewValue());
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setSource(l);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setSource(l);
        putFlightChange(f);
    }

    private void updateTo(TableColumn.CellEditEvent<Flight, String> t) {
        Location l = findLocationByName(t.getNewValue());
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setDestination(l);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setDestination(l);
        putFlightChange(f);
    }

    private void updateDepartureTime(TableColumn.CellEditEvent<Flight, String> t) {
        LocalDateTime time = LocalDateTime.parse(t.getNewValue(), formatter);
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setDeparture(time);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setDeparture(time);
        putFlightChange(f);
    }

    private void updateArrivalTime(TableColumn.CellEditEvent<Flight, String> t) {
        LocalDateTime time = LocalDateTime.parse(t.getNewValue(), formatter);
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setArrival(time);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setArrival(time);
        putFlightChange(f);
    }

    private void updateFlightCarrier(TableColumn.CellEditEvent<Flight, String> t) {
        String carrierName = t.getNewValue();
        Carrier c = findCarrierByName(carrierName);
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setCarrier(c);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setCarrier(c);
        f.setDeparture(f.getDeparture());
        f.setArrival(f.getArrival());
        putFlightChange(f);
    }

    private Carrier findCarrierByName(String carrierName) {
        for (Carrier c : carrierList) {
            if (c.getName().equals(carrierName)) return c;
        }
        return null;
    }

    private void putFlightChange(Flight f) {
        try {
            restClient.putObject(f, "/flight");
        } catch (IOException | InterruptedException err) {
            err.printStackTrace();
        }
    }

    private Location findLocationByName(String id){
        for (Location c : locationList) {
            if (c.getAirportId().equals(id)) return c;
        }
        return null;
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: zmienić z ComboBoxTableCell.forTableColumn na coś customowego co radzi sobie z obiektami
        initColumns();
        getFlights.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                var flightList = getFlights.getValue();
                listOfFlights = FXCollections.observableArrayList(flightList);
                dataTable.setItems(listOfFlights);
            }
        });
        getCarriers.setOnSucceeded(event -> {
            var carriers = getCarriers.getValue();
            this.carrierList =  getCarriers.getValue();
            listOfCarriers.addAll(carriers);
            carrierColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
                    FXCollections
                            .observableArrayList(
                                    listOfCarriers
                                            .stream()
                                            .map(Carrier::getName)
                                            .collect(Collectors.toList()))));
        });

        getLocation.setOnSucceeded(event -> {
            var locationsList = getLocation.getValue();
            this.locationList = locationsList;
            listOfLocations.addAll(locationsList);
            fromColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
                    FXCollections
                            .observableArrayList(
                                    listOfLocations
                                            .stream()
                                            .map(Location::getAirportId)
                                            .collect(Collectors.toList()))));

            toColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
                    FXCollections
                            .observableArrayList(
                                    listOfLocations
                                            .stream()
                                            .map(Location::getAirportId)
                                            .collect(Collectors.toList()))));

        });
        executorService.submit(getCarriers);
        executorService.submit(getFlights);
        executorService.submit(getLocation);
    }

}
