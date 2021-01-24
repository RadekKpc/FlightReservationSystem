package com.wesolemarcheweczki.frontend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Ticket {
    private Passenger passenger;

    private Flight flight;

    private Order order;

    private int seat;

    private int cost;

    private int id;

    @JsonIgnore
    boolean sent = false;

    private SimpleStringProperty nameProperty = new SimpleStringProperty("");
    private SimpleStringProperty lastNameProperty = new SimpleStringProperty("");
    private SimpleStringProperty fromProperty = new SimpleStringProperty("");
    private SimpleStringProperty toProperty = new SimpleStringProperty("");
    private SimpleStringProperty seatProperty = new SimpleStringProperty("");
    private SimpleStringProperty costProperty = new SimpleStringProperty("");
//    private boolean paid;

    public Ticket() {
    }

    public Ticket(int id, Passenger passenger, Flight f, Order o, int seat, int cost) {
        this.id = id;
        this.passenger = passenger;
        this.flight = f;
        this.order = o;
        this.seat = seat;
        this.cost = cost;
        nameProperty.set(passenger.getFirstName());
        lastNameProperty.set(passenger.getLastName());
        fromProperty.set(flight.getSource().getCity());
        toProperty.set(flight.getDestination().getCity());
        seatProperty.set(Integer.toString(seat));
        costProperty.set(Integer.toString(cost));
    }


    public Ticket(Passenger passenger, int seat, int cost, Flight f, Order o) {
        this.passenger = passenger;
        this.flight = f;
        this.order = o;
        this.seat = seat;
        this.cost = cost;
        nameProperty.set(passenger.getFirstName());
        lastNameProperty.set(passenger.getLastName());
        fromProperty.set(flight.getSource().getCity());
        toProperty.set(flight.getDestination().getCity());
        seatProperty.set(Integer.toString(seat));
        costProperty.set(Integer.toString(cost));
    }

    public Ticket(Passenger passenger, int seat, int cost) {
        this.passenger = passenger;
//        this.flight = flight;
//        this.order = order;s
        this.seat = seat;
        this.cost = cost;
        nameProperty.set(passenger.getFirstName());
        lastNameProperty.set(passenger.getLastName());
        seatProperty.set(Integer.toString(seat));
        costProperty.set(Integer.toString(cost));
    }
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
        nameProperty.set(passenger.getFirstName());
        lastNameProperty.set(passenger.getLastName());
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public SimpleStringProperty nameProperty() {
        return nameProperty;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
        fromProperty.set(flight.getSource().getCity());
        toProperty.set(flight.getDestination().getCity());
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
        seatProperty.set(Integer.toString(seat));
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
        costProperty.set(Integer.toString(cost));
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public SimpleStringProperty lastNameProperty() {
        return lastNameProperty;
    }

    public SimpleStringProperty fromProperty() {
        return fromProperty;
    }

    public SimpleStringProperty toProperty() {
        return toProperty;
    }

    public ObjectProperty<LocalDateTime> departureProperty() {
        return flight.departureProperty();
    }


    public ObjectProperty<LocalDateTime> arrivalProperty() {
        return flight.arrivalProperty();
    }


    public SimpleStringProperty seatProperty() {
        return seatProperty;
    }


    public SimpleStringProperty costProperty() {
        return costProperty;
    }
}
