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
import org.checkerframework.checker.units.qual.C;

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

public class CarriersStatsController implements Initializable {
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
                var toDate = LocalDate.parse(toDatePicker
                        .valueProperty()
                        .get()
                        .toString())
                        .atTime(23, 59);
                var fromDate = LocalDate
                        .parse(newValue.toString())
                        .atStartOfDay();
                carrierList.forEach(carrier -> {
                    var map = createCarrierStatsMap(carrier.getId(), fromDate, toDate);
                    try {
                        CarrierStats response = restClient.makeRequestWithBodyAndReturn(map,"/carrier/stats" ,"GET", true, CarrierStats.class);
                        carrierTotalCostMap.get(response.getCarrierId()).set((int) response.getTotalCost());
                        carrierTotalFlightsMap.get(response.getCarrierId()).set((int) response.getFlightsAmount());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        toDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            System.out.println(newValue.toString());
            System.out.println(newValue);
            if(fromDatePicker.valueProperty().get() != null && fromDatePicker.valueProperty().get().isBefore(newValue)){
                var toDate = LocalDate.parse(newValue
                        .toString())
                        .atTime(23, 59);
                var fromDate = LocalDate
                        .parse(fromDatePicker.valueProperty().get().toString())
                        .atStartOfDay();
                carrierList.forEach(carrier -> {
                    var map = createCarrierStatsMap(carrier.getId(), fromDate, toDate);
                    try {
                        CarrierStats response = restClient.makeRequestWithBodyAndReturn(map,"/carrier/stats" ,"GET", true, CarrierStats.class);
                        carrierTotalCostMap.get(response.getCarrierId()).set((int) response.getTotalCost());
                        carrierTotalFlightsMap.get(response.getCarrierId()).set((int) response.getFlightsAmount());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                updateLabels();
            }
        });
        executorService.submit(getCarriers);
    }

    private HashMap<String, Object> createCarrierStatsMap(int carrierId, LocalDateTime from, LocalDateTime to){
        HashMap<String, Object> map = new HashMap<>();
        map.put("carrierId", carrierId);
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
