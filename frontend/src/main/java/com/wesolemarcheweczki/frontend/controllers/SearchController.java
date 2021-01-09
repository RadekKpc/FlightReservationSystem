package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.search.CompositeSearchStrategy;
import com.wesolemarcheweczki.frontend.search.DateSearchStrategy;
import com.wesolemarcheweczki.frontend.search.PlaceSearchStrategy;
import com.wesolemarcheweczki.frontend.search.PriceSearchStrategy;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class SearchController {

    @FXML
    public ListView<Carrier> carrierList = new ListView<>();
    @FXML
    public TextField priceTextField;
    @FXML
    public DatePicker maxArr;
    @FXML
    public DatePicker minArr;
    @FXML
    public DatePicker maxDep;
    @FXML
    public DatePicker minDep;
    @FXML
    public TextField fromCity;
    @FXML
    public TextField fromCountry;
    @FXML
    public TextField toCity;
    @FXML
    public TextField toCountry;

    private FlightsController fc;

    private List<Flight> flightsList = new LinkedList<>();
    private final CompositeSearchStrategy css = new CompositeSearchStrategy();

    @FXML
    private void search() {
        priceStrategy();
        dateStrategy(minDep, maxDep, true);
        dateStrategy(minArr, maxArr, false);
        placeStrategy(fromCountry, fromCity, true);
        placeStrategy(toCountry, toCity, false);

        List<Flight> retFlights = new LinkedList<>();
        for (Flight f : flightsList) {
            if (css.filter(f))
                retFlights.add(f);
        }

        fc.setCurrFlights(retFlights);
        fc.updateFlights();
        Stage stage = (Stage) fromCountry.getScene().getWindow();
        stage.close();
    }

    private void priceStrategy() {
        if (!priceTextField.getText().isEmpty()) {
            int price = Integer.parseInt(priceTextField.getText());
            css.addSearchStrategy(new PriceSearchStrategy(price));
        }
    }

    private void placeStrategy(TextField fromCountry, TextField fromCity, boolean b) {
        if (!fromCountry.getText().isEmpty() && !fromCity.getText().isEmpty()) {
            addPlaceStrategy(fromCity, fromCountry, b);
        }
    }

    private void dateStrategy(DatePicker minDep, DatePicker maxDep, boolean b) {
        if (minDep.getValue() != null || maxDep.getValue() != null) {
            addDateStrategy(minDep, maxDep, b);
        }
    }

    private void addPlaceStrategy(TextField fromCity, TextField fromCountry, boolean b) {
        String city = fromCity.getText();
        String country = fromCountry.getText();
        css.addSearchStrategy(new PlaceSearchStrategy(city, country, b));
    }

    private void addDateStrategy(DatePicker minDep, DatePicker maxDep, boolean b) {
        LocalDate minDepTime = minDep.getValue();
        LocalDate maxDepTime = maxDep.getValue();
        if (minDep.getValue() != null) maxDepTime = LocalDate.of(2100, 12, 31);
        if (maxDep.getValue() != null) minDepTime = LocalDate.of(1900, 12, 31);
        css.addSearchStrategy(new DateSearchStrategy(minDepTime, maxDepTime, b));
    }

    public void setFlightsController(FlightsController fc) {
        this.fc = fc;
        this.flightsList = fc.getFlightsList();
    }
}
