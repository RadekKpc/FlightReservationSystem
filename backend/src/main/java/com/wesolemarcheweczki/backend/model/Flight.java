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
    private int baseCost;

    @ManyToOne
    private Location destination;
    @ManyToOne
    private Location source;

    public Flight(Flight flight) {
        this(flight.flightCode, flight.carrier, flight.departure, flight.arrival, flight.capacity, flight.baseCost, flight.destination, flight.source);
    }

    public Flight(String flightCode, Carrier carrier, LocalDateTime departure, LocalDateTime arrival, int capacity, int baseCost, Location destination, Location source) {
        this.flightCode = flightCode;
        this.carrier = carrier;
        this.departure = departure;
        this.arrival = arrival;
        this.capacity = capacity;
        this.baseCost = baseCost;
        this.destination = destination;
        this.source = source;
    }

    public Flight() {

    }

    @Override
    public Flight copy() {
        return new Flight(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public void setParams(Flight object) {
        flightCode = object.flightCode;
        carrier = object.carrier;
        departure = object.departure;
        arrival = object.arrival;
        capacity = object.capacity;
        baseCost = object.baseCost;
        destination = object.getDestination();
        source = object.getSource();
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

    public int getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(int baseCost) {
        this.baseCost = baseCost;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightCode='" + flightCode + '\'' +
                ", carrier=" + carrier +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", capacity=" + capacity +
                ", baseCost=" + baseCost +
                ", destination=" + destination +
                ", source=" + source +
                '}';
    }
}
