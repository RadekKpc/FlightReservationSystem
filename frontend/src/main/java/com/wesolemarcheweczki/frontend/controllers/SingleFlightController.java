package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Flight;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SingleFlightController{

    @FXML
    private Label carrierName;
    @FXML
    private Label sourceCountry;
    @FXML
    private Label destinationCountry;
    @FXML
    private Label sourceAirport;
    @FXML
    private Label destinationAirport;
    @FXML
    private Label departureDate;
    @FXML
    private Label arrivalDate;
    @FXML
    private Label flightCode;
    @FXML
    private Label baseCost;
    @FXML
    private Label bookedPlaces;
    @FXML
    private Label freePlaces;
    @FXML
    private Button editFlightButton;
    @FXML
    private Button deleteFlightButton;
    private Flight f;

    public void setData(Flight f) {
        this.f = f;
        carrierName.setText(f.getCarrier().getName());
        sourceCountry.setText(f.getSource().getCountry());
        destinationCountry.setText(f.getDestination().getCountry());
        sourceAirport.setText(f.getSource().getAirportId());
        destinationAirport.setText(f.getDestination().getAirportId());
        departureDate.setText(f.getDeparture().toString());
        arrivalDate.setText(f.getArrival().toString());
        flightCode.setText(f.getFlightCode());
        baseCost.setText(String.valueOf(f.getBaseCost()));

        // todo
        bookedPlaces.setText(String.valueOf(f.getCapacity()));
        freePlaces.setText("test");
    }

    @FXML
    private void editFlightDetails() {
        //carrierName.setText("changed");

    }
    @FXML
    private void deleteFlight() {

    }

}
