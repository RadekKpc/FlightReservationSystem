package com.wesolemarcheweczki.frontend.model;

import java.time.LocalDateTime;

public class Flight {
    private String flightCode;

    private Carrier carrier;

    private LocalDateTime departure;
    private LocalDateTime arrival;
    private int capacity;
    private Location destination;
    private Location source;

    public Flight() {

    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Location getSource() {
        return source;
    }

    public void setSource(Location source) {
        this.source = source;
    }

    public Flight(String flightCode, Carrier carrier, LocalDateTime departure, LocalDateTime arrival, int capacity, Location destination, Location source) {
        this.flightCode = flightCode;
        this.carrier = carrier;
        this.departure = departure;
        this.arrival = arrival;
        this.capacity = capacity;
        this.destination = destination;
        this.source = source;
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
