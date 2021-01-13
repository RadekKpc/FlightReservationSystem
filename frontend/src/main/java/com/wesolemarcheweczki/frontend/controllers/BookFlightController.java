package com.wesolemarcheweczki.frontend.controllers;

import com.wesolemarcheweczki.frontend.model.Flight;
import com.wesolemarcheweczki.frontend.model.Order;
import com.wesolemarcheweczki.frontend.model.Passenger;
import com.wesolemarcheweczki.frontend.model.Ticket;
import com.wesolemarcheweczki.frontend.restclient.RestClient;
import com.wesolemarcheweczki.frontend.util.AuthManager;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class BookFlightController implements Initializable {

    private final RestClient restClient = new RestClient();
    private final Order order = new Order(RestClient.getLoggedClient());
    private final List<Ticket> tickets = new ArrayList<>();
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
    private Flight flight;
    private FlightsController flightsController;

    public void setFlight(Flight f) {
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
        bookButton.setOnAction(this::bookOrder);

        addAndContinueButton.setOnAction(this::addAndConinue);

        totalLabel.textProperty().bind(total.asString());
        seatLabel.textProperty().bind(seat.asString());
    }

    public void setFlightsController(FlightsController flightsController) {
        this.flightsController = flightsController;
    }

    private HashMap createPostObject() {
        HashMap<String, Object> obj = createOrderMap(order.getClient().getEmail());
        obj.put("tickets", tickets);
        return obj;
    }

    private void addAndConinue(ActionEvent e) {
        Passenger p = new Passenger(nameInput.getText(), surnameInput.getText());
        Ticket t = new Ticket(p, seat.get(), flight.getBaseCost());
        tickets.add(t);
        seat.set(seat.getValue() + 1);
        total.set(total.get() + flight.getBaseCost());
    }

    private void bookOrder(ActionEvent event) {
        //check flight, then create and send request
        HashMap<String, Object> map = createOrderMap(AuthManager.email);
        try {
            var notColliding = restClient.check("/flight/collision", map);
            if (notColliding) {
                postOrder(event);
            } else {
                System.out.println("You already have ticket for this termin!");
            }
        } catch (Exception e) {
            System.out.println("something went wrong");
            e.printStackTrace();
        }
    }

    private HashMap<String, Object> createOrderMap(String email) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("flightId", flight.getId());
        return map;
    }

    private void postOrder(ActionEvent event) throws IOException, InterruptedException {
        var successfullyPosted = restClient.postObject(createPostObject(), "/order/create");
        if (successfullyPosted) {
            updateUIAfterPosting(event);
        } else {
            System.out.println("Failed saving order");
        }
    }

    private void updateUIAfterPosting(ActionEvent event) {
        flightsController.resetFlights();
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
