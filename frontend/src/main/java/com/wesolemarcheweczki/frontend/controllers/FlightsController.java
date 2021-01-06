package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.model.Location;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import com.wesolemarcheweczki.frontend.util.AuthManager;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    public Button addFlightButton;
    public TextField priceCombo;
    public TextField departureCombo;
    public ComboBox<Location> toCombo;
    public TextField arrivalCombo;
    public ComboBox<Location> fromCombo;
    public ComboBox<Carrier> addCarrierCombo;
    public TextField placesCombo;
    public Button deleteButton;
    public Button reserveButton;
    public Label errorLabel;
    private final ObservableList<Flight> listOfFlights = FXCollections.observableArrayList();
    private final ObservableList<Carrier> listOfCarriers = FXCollections.observableArrayList();
    private final ObservableList<Location> listOfLocations = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Flight, String> freeColumn;
    @FXML
    private TableColumn<Flight, String> placesColumn;
    @FXML
    private TableColumn<Flight, String> priceColumn;
    @FXML
    private TableColumn<Flight, String> arrivalColumn;
    @FXML
    private TableColumn<Flight, String> departureColumn;
    @FXML
    private TableColumn<Flight, Location> fromColumn;
    @FXML
    private TableColumn <Flight, Location> toColumn;
    @FXML
    private TableColumn<Flight, Carrier> carrierColumn;
    @FXML
    private TableView<Flight> dataTable;
    @FXML
    private VBox flightContainer;
    @FXML
    private Label flightsLabel;
    @FXML
    private Label bookedLabel;
    @FXML
    private Label freeLabel;

    private Stage searchStage;
    @FXML
    private final RestClient restClient = new RestClient();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Task<List<Flight>> getFlights = restClient.createGetTask("/flight",Flight.class);

    private final Task<List<Carrier>> getCarriers = restClient.createGetTask("/carrier", Carrier.class);

    private final Task<List<Location>> getLocation = restClient.createGetTask("/location", Location.class);

    public List<Flight> getFlightsList() {
        return flightsList;
    }

    public void setFlightsList(List<Flight> flightsList) {
        this.flightsList = flightsList;
    }

    public List<Flight> getCurrFlights() {
        return currFlights;
    }

    public void setCurrFlights(List<Flight> currFlights) {
        this.currFlights = currFlights;
    }

    public List<Flight> flightsList;
    public List<Flight> currFlights;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private void adjustToRole(){
        var isAdmin = AuthManager.isAdmin();
        dataTable.setEditable(isAdmin);
        addCarrierCombo.setVisible(isAdmin);
        toCombo.setVisible(isAdmin);
        fromCombo.setVisible(isAdmin);
        arrivalCombo.setVisible(isAdmin);
        departureCombo.setVisible(isAdmin);
        placesCombo.setVisible(isAdmin);
        addFlightButton.setVisible(isAdmin);
        deleteButton.setVisible(isAdmin);
        priceCombo.setVisible(isAdmin);
    }

    private void initColumns(){
        adjustToRole();
        deleteButton.setOnAction(event -> {
            Flight f = dataTable.getSelectionModel().getSelectedItem();
            listOfFlights.remove(f);
            try {
                restClient.deleteObject("/flight", f);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        addFlightButton.setOnAction(event -> {
            int baseCost = Integer.parseInt(priceCombo.getText());
            int capacity = Integer.parseInt(placesCombo.getText());
            int flightCode = listOfFlights.stream().map(Flight::getId).max((a, b) -> a - b).orElse(0);
            Flight f = new Flight(flightCode,"flight" + flightCode,
                    addCarrierCombo.getValue(),
                    LocalDateTime.parse(departureCombo.getText(), formatter),
                    LocalDateTime.parse(arrivalCombo.getText(), formatter),
                    capacity, baseCost, fromCombo.getValue(), toCombo.getValue());
            try {
                var res = restClient.postObject(f, "/flight");
                System.out.println(res);
                listOfFlights.add(f);
                currFlights = listOfFlights;
                //dataTable.setItems(listOfFlights);
                priceCombo.setText("");
                placesCombo.setText("");
                addCarrierCombo.setValue(null);
                departureCombo.setText("");
                arrivalCombo.setText("");
                fromCombo.setValue(null);
                toCombo.setValue(null);
                //this.dataTable.setItems(FXCollections.observableArrayList(flightsList));
                updateLabels();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        //change view dependant of user role - admin/client
        if(RestClient.getLoggedClient().isAdmin()) {
            reserveButton.setVisible(false);
        } else {
            addCarrierCombo.setVisible(false);
            fromCombo.setVisible(false);
            toCombo.setVisible(false);
            departureCombo.setVisible(false);
            arrivalCombo.setVisible(false);
            priceCombo.setVisible(false);
            addFlightButton.setVisible(false);
            placesCombo.setVisible(false);
            deleteButton.setVisible(false);
        }

        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getBaseCost())));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        freeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getFreePlaces())));
        freeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        arrivalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrival().toString()));
        arrivalColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departureColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getDeparture().toString()));
        departureColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fromColumn.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());
        toColumn.setCellValueFactory(cellData -> cellData.getValue().destinationProperty());
        carrierColumn.setCellValueFactory(cellData -> cellData.getValue().carrierProperty());
        placesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCapacity())));

        placesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        carrierColumn.setCellFactory(ComboBoxTableCell.forTableColumn(listOfCarriers));
        fromColumn.setCellFactory(ComboBoxTableCell.forTableColumn(listOfLocations));
        toColumn.setCellFactory(ComboBoxTableCell.forTableColumn(listOfLocations));

        priceColumn.setOnEditCommit(this::updateFlightCost);
        carrierColumn.setOnEditCommit(this::updateFlightCarrier);
        arrivalColumn.setOnEditCommit(this::updateArrivalTime);
        departureColumn.setOnEditCommit(this::updateDepartureTime);
        fromColumn.setOnEditCommit(this::updateFrom);
        toColumn.setOnEditCommit(this::updateTo);
        placesColumn.setOnEditCommit(this::updatePlaces);
        freeColumn.setEditable(false);

        addCarrierCombo.setItems(listOfCarriers);
        fromCombo.setItems(listOfLocations);
        toCombo.setItems(listOfLocations);
        dataTable.setItems(listOfFlights);

    }

    private <T> Flight getCurrentObject(TableColumn.CellEditEvent<Flight, T> t){
        return t.getTableView().getItems().get(t.getTablePosition().getRow());
    }

    private void updatePlaces(TableColumn.CellEditEvent<Flight, String> t) {
        int places = Integer.parseInt(t.getNewValue());
        Flight f = getCurrentObject(t);
        f.setCapacity(places);
        putFlightChange(f);
    }

    private void updateFlightCost(TableColumn.CellEditEvent<Flight, String> t) {
        int cost = Integer.parseInt(t.getNewValue());
        Flight f = getCurrentObject(t);
        f.setBaseCost(cost);
        putFlightChange(f);
    }

    private void updateFrom(TableColumn.CellEditEvent<Flight, Location> t) {
        Location l = t.getNewValue();
        Flight f = getCurrentObject(t);;
        f.setSource(l);
        putFlightChange(f);
    }

    private void updateTo(TableColumn.CellEditEvent<Flight, Location> t) {
        Location l = t.getNewValue();
        Flight f = getCurrentObject(t);
        f.setDestination(l);
        putFlightChange(f);
    }

    private void updateDepartureTime(TableColumn.CellEditEvent<Flight, String> t) {
        LocalDateTime time = LocalDateTime.parse(t.getNewValue(), formatter);
        Flight f = getCurrentObject(t);
        f.setDeparture(time);
        putFlightChange(f);
    }

    private void updateArrivalTime(TableColumn.CellEditEvent<Flight, String> t) {
        LocalDateTime time = LocalDateTime.parse(t.getNewValue(), formatter);
        Flight f = getCurrentObject(t);
        f.setArrival(time);
        putFlightChange(f);
    }

    private void updateFlightCarrier(TableColumn.CellEditEvent<Flight, Carrier> t) {
        Carrier c = t.getNewValue();
        Flight f = getCurrentObject(t);;
        f.setCarrier(c);
        putFlightChange(f);
    }

    private void putFlightChange(Flight f) {
        try {
            restClient.putObject(f, "/flight");
        } catch (IOException | InterruptedException err) {
            err.printStackTrace();
        }
    }

    @FXML
    public void search() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/searchFlights.fxml"));
        Parent root = loader.load();
        SearchController sc = loader.getController();
        sc.setFlightsController(this);
        searchStage = new Stage();
        Scene scene = new Scene(root);
        searchStage.setResizable(false);
        searchStage.setScene(scene);
        searchStage.show();
    }

    public void updateFlights() {
        //this.dataTable.setItems(FXCollections.observableArrayList(currFlights));
        this.listOfFlights.removeAll(this.flightsList);
        this.listOfFlights.addAll(this.currFlights);
        updateLabels();
    }

    @FXML
    public void reserveFlight() throws IOException {
        Flight f = dataTable.getSelectionModel().getSelectedItem();
        if (f == null) {
            errorLabel.setText("You didn't select Flight");
        } else {
            errorLabel.setText("");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BookFlight.fxml"));
            Parent root = loader.load();
            var bookStage = new Stage();
            Scene scene = new Scene(root);
            BookFlightController bc = loader.getController();
            bc.setFlight(f);
            bookStage.setResizable(false);
            bookStage.setScene(scene);
            bookStage.show();
            // open new reservation panel
            // check if flight is in the time of another reserved time
        }
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
                listOfFlights.addAll(flightList);
                flightsList = flightList;
                currFlights = flightList;
                updateLabels();
            }
        });
        getCarriers.setOnSucceeded(event -> {
            var carriers = getCarriers.getValue();
            listOfCarriers.addAll(carriers);
        });

        getLocation.setOnSucceeded(event -> {
            var locationsList = getLocation.getValue();
            listOfLocations.addAll(locationsList);
        });
        executorService.submit(getCarriers);
        executorService.submit(getFlights);
        executorService.submit(getLocation);
    }

    private void updateLabels() {
        bookedLabel.setText("0");
        int freePlaces = 0;
        for (Flight f : currFlights) freePlaces += f.getCapacity();
        freeLabel.setText(String.valueOf(freePlaces));
        flightsLabel.setText(String.valueOf(currFlights.size()));
    }

}
