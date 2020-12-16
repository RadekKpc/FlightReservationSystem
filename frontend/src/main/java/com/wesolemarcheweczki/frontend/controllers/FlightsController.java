package com.wesolemarcheweczki.frontend.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.model.Location;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.beans.property.SimpleSetProperty;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class FlightsController implements Initializable {

    private ObservableList<Flight> listOfFlights;
    private ObservableList<Carrier> listOfCarriers = FXCollections.observableArrayList();
    private ObservableList<Location> listOfLocations = FXCollections.observableArrayList();

    public TableColumn freeColumn;
    public TableColumn bookedColumn;
    public TableColumn<Flight, String> priceColumn;
    public TableColumn<Flight, String> arrivalColumn;
    public TableColumn<Flight, String> departureColumn;
    public TableColumn<Flight, String> fromColumn;
    public TableColumn <Flight, String> toColumn;
    public TableColumn<Flight, String> carrierColumn;
    public TableView<Flight> dataTable;
    @FXML
    private VBox flightContainer;

    @FXML
    
    
    private final RestClient restClient = new RestClient();

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Task<List<Flight>> getFlights = restClient.createGetTask("/flight",Flight.class);

    private final Task<List<Carrier>> getCarriers = restClient.createGetTask("/carrier", Carrier.class);

    private final Task<List<Location>> getLocation = restClient.createGetTask("/location", Location.class);

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
        fromColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSource().getCity()));
        toColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDestination().getCity()));
        carrierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCarrier().getName()));
        priceColumn.setOnEditCommit(
                (TableColumn.CellEditEvent<Flight, String> t) ->
                        ( t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setBaseCost(Integer.parseInt(t.getNewValue()))
        );
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        //TODO: zmienić z ComboBoxTableCell.forTableColumn na coś customowego co radzi sobie z obiektami
        initColumns();
        getFlights.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("succeeded");
                var flightList = getFlights.getValue();
                listOfFlights = FXCollections.observableArrayList(flightList);
                dataTable.setItems(listOfFlights);
            }
        });
        getCarriers.setOnSucceeded(event -> {
            var carrierList = getCarriers.getValue();
            System.out.println(carrierList);
            listOfCarriers.addAll(carrierList);
            carrierColumn.setCellFactory(ComboBoxTableCell.forTableColumn(
                    FXCollections
                            .observableArrayList(
                                    listOfCarriers
                                            .stream()
                                            .map(Carrier::getName)
                                            .collect(Collectors.toList()))));
        });

        getLocation.setOnSucceeded(event -> {
            System.out.println("location success");
            var locationsList = getLocation.getValue();
            System.out.println("locations " + locationsList);
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
