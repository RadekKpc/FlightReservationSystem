package com.wesolemarcheweczki.frontend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.*;

import java.time.LocalDateTime;

public class Flight {

    private int id;
    private StringProperty flightCode;
    private ObjectProperty<Carrier> carrier;
    private ObjectProperty<LocalDateTime> departure;
    private ObjectProperty<LocalDateTime> arrival;
    private IntegerProperty capacity;
    private IntegerProperty baseCost;
    private ObjectProperty<Location> destination;
    private ObjectProperty<Location> source;
    private IntegerProperty freePlaces;

    @JsonCreator
    public Flight(@JsonProperty("id") int id,@JsonProperty("flightCode") String flightCode, @JsonProperty("carrier") Carrier carrier,@JsonProperty("departure") LocalDateTime departure, @JsonProperty("arrival") LocalDateTime arrival,@JsonProperty("capacity") int capacity, @JsonProperty("destination") Location destination, @JsonProperty("source") Location source, @JsonProperty("baseCost") int baseCost, @JsonProperty("freePlaces") int freePlaces) {
        this.id = id;
        this.flightCode = new SimpleStringProperty(flightCode);
        this.carrier = new SimpleObjectProperty<>(carrier);
        this.departure = new SimpleObjectProperty<>(departure);
        this.arrival = new SimpleObjectProperty<>(arrival);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.baseCost = new SimpleIntegerProperty(baseCost);
        this.destination = new SimpleObjectProperty<>(destination);
        this.source = new SimpleObjectProperty<>(source);
        this.freePlaces = new SimpleIntegerProperty(freePlaces);
    }

    public Flight(String flightCode, Carrier carrier, LocalDateTime departure, LocalDateTime arrival, int capacity, int baseCost, Location destination, Location source) {
        this.flightCode = new SimpleStringProperty(flightCode);
        this.carrier = new SimpleObjectProperty<>(carrier);
        this.departure = new SimpleObjectProperty<>(departure);
        this.arrival = new SimpleObjectProperty<>(arrival);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.baseCost = new SimpleIntegerProperty(baseCost);
        this.destination = new SimpleObjectProperty<>(destination);
        this.source = new SimpleObjectProperty<>(source);
    }

    public Flight(int id, String flightCode, Carrier carrier, LocalDateTime departure, LocalDateTime arrival, int capacity, int baseCost, Location destination, Location source) {
        this.id = id;
        this.flightCode = new SimpleStringProperty(flightCode);
        this.carrier = new SimpleObjectProperty<>(carrier);
        this.departure = new SimpleObjectProperty<>(departure);
        this.arrival = new SimpleObjectProperty<>(arrival);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.baseCost = new SimpleIntegerProperty(baseCost);
        this.destination = new SimpleObjectProperty<>(destination);
        this.source = new SimpleObjectProperty<>(source);
        this.freePlaces = new SimpleIntegerProperty(capacity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightCode() {
        return flightCode.get();
    }

    public StringProperty flightCodeProperty() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode.set(flightCode);
    }

    public Carrier getCarrier() {
        return carrier.get();
    }

    public ObjectProperty<Carrier> carrierProperty() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier.set(carrier);
    }

    public LocalDateTime getDeparture() {
        return departure.get();
    }

    public ObjectProperty<LocalDateTime> departureProperty() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure.set(departure);
    }

    public LocalDateTime getArrival() {
        return arrival.get();
    }

    public ObjectProperty<LocalDateTime> arrivalProperty() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival.set(arrival);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public IntegerProperty capacityProperty() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public int getBaseCost() {
        return baseCost.get();
    }

    public IntegerProperty baseCostProperty() {
        return baseCost;
    }

    public void setBaseCost(int baseCost) {
        this.baseCost.set(baseCost);
    }

    public Location getDestination() {
        return destination.get();
    }

    public ObjectProperty<Location> destinationProperty() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination.set(destination);
    }

    public Location getSource() {
        return source.get();
    }

    public ObjectProperty<Location> sourceProperty() {
        return source;
    }

    public void setSource(Location source) {
        this.source.set(source);
    }


    public int getFreePlaces() {
        return freePlaces.get();
    }

    public IntegerProperty freePlacesProperty() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces.set(freePlaces);
    }

}

