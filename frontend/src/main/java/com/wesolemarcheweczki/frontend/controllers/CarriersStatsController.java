package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.model.CarrierStats;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CarriersStatsController implements Initializable {
    public Label errorLabel;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    public Label carriersLabel;
    public Label costLabel;
    public Label flightsLabel;
    private ObservableList<Carrier> carrierList = FXCollections.observableArrayList();
    private ObjectProperty<LocalDateTime> from;
    private ObjectProperty<LocalDateTime> to;
    private IntegerProperty totalCost;
    private IntegerProperty totalFlights;
    private HashMap<Integer, IntegerProperty> carrierTotalCostMap = new HashMap<>();
    private HashMap<Integer, IntegerProperty> carrierTotalFlightsMap = new HashMap<>();
    private RestClient restClient = new RestClient();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Task<List<Carrier>> getCarriers = restClient.createGetTask("/carrier", Carrier.class);


    @FXML
    private TableView<Carrier> dataTable;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private TableColumn<Carrier, String> nameColumn;
    @FXML
    private TableColumn<Carrier, Integer> totalCostColumn;
    @FXML
    private TableColumn<Carrier, Integer> totalFlightsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataTable.setEditable(false);
        dataTable.setItems(carrierList);
        getCarriers.setOnSucceeded(e -> {
            nameColumn.setCellValueFactory(carrier -> carrier.getValue().nameProperty());
            totalCostColumn.setCellValueFactory(carrier -> carrierTotalCostMap.get(carrier.getValue().getId()).asObject());
            totalFlightsColumn.setCellValueFactory(carrier -> carrierTotalFlightsMap.get(carrier.getValue().getId()).asObject());
            var carriers = getCarriers.getValue();
            carriers.forEach(carrier -> {
                carrierTotalCostMap.put(carrier.getId(), new SimpleIntegerProperty(0));
                carrierTotalFlightsMap.put(carrier.getId(), new SimpleIntegerProperty(0));
            });
            carrierList.addAll(carriers);
            updateLabels();
        });
        fromDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(toDatePicker.valueProperty().get() != null && toDatePicker.valueProperty().get().isAfter(newValue)){
                errorLabel.setVisible(false);
                var toDate = LocalDate.parse(toDatePicker
                        .valueProperty()
                        .get()
                        .toString())
                        .atTime(23, 59);
                var fromDate = LocalDate
                        .parse(newValue.toString())
                        .atStartOfDay();
                handleDateChanged(fromDate, toDate);
            }
            else if(toDatePicker.valueProperty().get() != null) errorLabel.setVisible(true);
        });
        toDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            if(fromDatePicker.valueProperty().get() != null && fromDatePicker.valueProperty().get().isBefore(newValue)){
                errorLabel.setVisible(false);
                var toDate = LocalDate.parse(newValue
                        .toString())
                        .atTime(23, 59);
                var fromDate = LocalDate
                        .parse(fromDatePicker.valueProperty().get().toString())
                        .atStartOfDay();
                handleDateChanged(fromDate, toDate);
            }
            else if(fromDatePicker.valueProperty().get() != null) errorLabel.setVisible(true);
        });
        executorService.submit(getCarriers);
    }

    private void handleDateChanged(LocalDateTime fromDate, LocalDateTime toDate){
        var requestBody = createCarrierStatsRequestBodyMap(fromDate, toDate);
        var task = restClient.createGetTaskWithBody("/carrier/stats",
                requestBody,
                CarrierStats.class);
        task.setOnSucceeded(e -> {
            var listOfStats = task.getValue();
            listOfStats.forEach(stat -> {
                carrierTotalFlightsMap.get(stat.getCarrierId()).set((int)stat.getFlightsAmount());
                carrierTotalCostMap.get(stat.getCarrierId()).set((int)stat.getTotalCost());
            });
            updateLabels();
        });
        executorService.submit(task);
    }

    private HashMap<String, Object> createCarrierStatsRequestBodyMap(LocalDateTime from, LocalDateTime to){
        HashMap<String, Object> map = new HashMap<>();
        map.put("from", from);
        map.put("to", to);
        return map;
    }

    private void updateLabels(){
        long getFullCost = carrierTotalCostMap.values().stream().reduce(new SimpleIntegerProperty(0),
                (a, b) -> {
            a.set(a.get() + b.get());
            return a;
        }).get();

        long getFullFlights = carrierTotalFlightsMap.values().stream().reduce(new SimpleIntegerProperty(0),
                (a, b) -> {
                    a.set(a.get() + b.get());
                    return a;
                }).get();

        costLabel.setText(String.valueOf(getFullCost));
        flightsLabel.setText(String.valueOf(getFullFlights));
        carriersLabel.setText(String.valueOf(carrierList.size()));
    }
}
