package com.wesolemarcheweczki.frontend.controllers;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.model.Order;
import com.wesolemarcheweczki.frontend.model.Passenger;
import com.wesolemarcheweczki.frontend.model.Ticket;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import com.wesolemarcheweczki.frontend.util.AuthManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.checkerframework.checker.units.qual.C;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class BookFlightController implements Initializable {

    private final RestClient restClient = new RestClient();
    public TextField nameInput;
    public TextField surnameInput;
    //public CheckBox profileDataCheckBox;
    public Label carrierLabel;
    public Label fromLabel;
    public Label toLabel;
    public Label departureLabel;
    public Label arrivalLabel;
    public Label seatLabel;
    public Label costLabel;
    public Label totalLabel;
    public Button bookButton;
    public Button addAndContinueButton;
    //public Button cancelButton;
    private IntegerProperty seat;
    private IntegerProperty total;
    private Order order = new Order(AuthManager.email);
    private List<Ticket> tickets = new ArrayList<>();
    private Flight flight;

    public void setFlight(Flight f){
        carrierLabel.setText(f.getCarrier().toString());
        fromLabel.setText(f.getSource().toString());
        toLabel.setText(f.getDestination().toString());
        departureLabel.setText(f.getDeparture().toString());
        arrivalLabel.setText(f.getArrival().toString());
        costLabel.setText(Integer.toString(f.getBaseCost()));
        seat.set(f.getCapacity() - f.getFreePlaces() + 1);
        total.set(0);
        this.flight = f;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        seat = new SimpleIntegerProperty(0);
        total = new SimpleIntegerProperty(0);
        bookButton.setOnAction(e -> {
            //check flight, then create and send request
        });

        addAndContinueButton.setOnAction(e -> {
            Passenger p = new Passenger(nameInput.getText(), surnameInput.getText());
            Ticket t = new Ticket(p, seat.get(), flight.getBaseCost());
            tickets.add(t);
            seat.set(seat.getValue() + 1);
            total.set(total.get() + flight.getBaseCost());
        });

        totalLabel.textProperty().bind(total.asString());
        seatLabel.textProperty().bind(seat.asString());
    }

    private HashMap createPostObject(){
        HashMap<String, Object> obj = new HashMap<>();
        obj.put("email", order.getClientEmail());
        obj.put("flightId", flight.getId());
        obj.put("tickets", tickets);
        return obj;
    }
}
