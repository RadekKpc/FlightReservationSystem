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
    private ObservableList<Flight> listOfFlights;
    private ObservableList<Carrier> listOfCarriers = FXCollections.observableArrayList();
    private ObservableList<Location> listOfLocations = FXCollections.observableArrayList();
    @FXML
    private TableColumn freeColumn;
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

    public List<Flight> flightsList;
    public List<Flight> currFlights;
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
        deleteButton.setOnAction(event -> {
            Flight f = dataTable.getSelectionModel().getSelectedItem();
            listOfFlights.remove(f);
            //restClient.deleteObject("/flight", f);
        });
        addFlightButton.setOnAction(event -> {
            int baseCost = Integer.parseInt(priceCombo.getText());
            int capacity = Integer.parseInt(placesCombo.getText());
            int flightCode = listOfFlights.stream().map(Flight::getId).max((a, b) -> a - b).orElse(0);
            Flight f = new Flight(flightCode,"flight" + flightCode,
                    addCarrierCombo.getValue(),
                    LocalDateTime.parse(departureCombo.getText(), formatter),
                    LocalDateTime.parse(arrivalCombo.getText(), formatter),
                    capacity, fromCombo.getValue(), toCombo.getValue(), baseCost);
            try {
                restClient.postObject(f, "/flight");
                flightsList.add(f);
                currFlights = flightsList;
                listOfFlights = FXCollections.observableArrayList(flightsList);
                dataTable.setItems(listOfFlights);
                priceCombo.setText("");
                placesCombo.setText("");
                addCarrierCombo.setValue(null);
                departureCombo.setText("");
                arrivalCombo.setText("");
                fromCombo.setValue(null);
                toCombo.setValue(null);
                this.dataTable.setItems(FXCollections.observableArrayList(flightsList));
                updateLabels();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });


        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getBaseCost())));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        arrivalColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArrival().toString()));
        arrivalColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        departureColumn.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getDeparture().toString()));
        departureColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        fromColumn.setCellValueFactory(cellData -> cellData.getValue().sourceProperty());
        toColumn.setCellValueFactory(cellData -> cellData.getValue().destinationProperty());
        carrierColumn.setCellValueFactory(cellData -> cellData.getValue().carrierProperty());
        placesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getCapacity())));
        placesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setOnEditCommit(this::updateFlightCost);
        carrierColumn.setOnEditCommit(this::updateFlightCarrier);
        arrivalColumn.setOnEditCommit(this::updateArrivalTime);
        departureColumn.setOnEditCommit(this::updateDepartureTime);
        fromColumn.setOnEditCommit(this::updateFrom);
        toColumn.setOnEditCommit(this::updateTo);
        placesColumn.setOnEditCommit(this::updateFlightPlaces);
    }

    private void updateFlightPlaces(TableColumn.CellEditEvent<Flight, String> t){
        int cap = Integer.parseInt(t.getNewValue());
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setCapacity(cap);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setBaseCost(cap);
        putFlightChange(f);
        updateLabels();
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

    private void updateFrom(TableColumn.CellEditEvent<Flight, Location> t) {
        Location l = t.getNewValue();
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setSource(l);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setSource(l);
        putFlightChange(f);
    }

    private void updateTo(TableColumn.CellEditEvent<Flight, Location> t) {
        Location l = t.getNewValue();
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

    private void updateFlightCarrier(TableColumn.CellEditEvent<Flight, Carrier> t) {
        Carrier c = t.getNewValue();
        t.getTableView()
                .getItems()
                .get(t.getTablePosition().getRow())
                .setCarrier(c);
        Flight f = t.getTableView().getItems().get(t.getTablePosition().getRow());
        f.setCarrier(c);
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

    @FXML
    public void search() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/searchFlights.fxml"));
        Parent root = loader.load();
        SearchController sc = loader.getController();
        sc.setFlightsController(this);
        searchStage = new Stage();
//        Parent root = FXMLLoader.load(getClass().getResource("/views/searchFlights.fxml"));
        Scene scene = new Scene(root);
        searchStage.setResizable(false);
        searchStage.setScene(scene);
        searchStage.show();
    }

    public void updateFlights() {
        this.dataTable.setItems(FXCollections.observableArrayList(currFlights));
        updateLabels();
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
                flightsList = flightList;
                currFlights = flightList;
                updateLabels();
            }
        });
        getCarriers.setOnSucceeded(event -> {
            var carriers = getCarriers.getValue();
            this.carrierList =  getCarriers.getValue();
            listOfCarriers.addAll(carriers);
            carrierColumn.setCellFactory(ComboBoxTableCell.forTableColumn(listOfCarriers));

            addCarrierCombo.setItems(listOfCarriers);
        });

        getLocation.setOnSucceeded(event -> {
            var locationsList = getLocation.getValue();
            this.locationList = locationsList;
            listOfLocations.addAll(locationsList);
            fromColumn.setCellFactory(ComboBoxTableCell.forTableColumn(listOfLocations));

            toColumn.setCellFactory(ComboBoxTableCell.forTableColumn(listOfLocations));

            fromCombo.setItems(listOfLocations);
            toCombo.setItems(listOfLocations);
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
