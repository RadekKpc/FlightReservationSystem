package com.wesolemarcheweczki.backend.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Flight implements AbstractModel<Flight> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String flightCode;

    @ManyToOne
    private Carrier carrier;

    private LocalDateTime departure;
    private LocalDateTime arrival;
    private int capacity;

    public Flight() {

    }

    public Flight(String flightCode, Carrier carrier, LocalDateTime departure, LocalDateTime arrival, int capacity) {
        this.flightCode = flightCode;
        this.carrier = carrier;
        this.departure = departure;
        this.arrival = arrival;
        this.capacity = capacity;
    }

    @Override
    public Flight copy() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setParams(Flight object) {
        flightCode = object.flightCode;
        carrier = object.carrier;
        departure = object.departure;
        arrival = object.arrival;
        capacity = object.capacity;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
