package com.wesolemarcheweczki.frontend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Location {

    private int id;
    private StringProperty airportId;
    private StringProperty city;
    private StringProperty country;

    public Location(){}

    public int getId() {
        return id;
    }

    public String toString(){
        return getAirportId();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirportId() {
        return airportId.get();
    }

    public StringProperty airportIdProperty() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId.set(airportId);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    @JsonCreator
    public Location(@JsonProperty("airportId") String airportId, @JsonProperty("city") String city, @JsonProperty("country") String country) {
        this.airportId = new SimpleStringProperty(airportId);
        this.city = new SimpleStringProperty(city);
        this.country = new SimpleStringProperty(country);
    }

}
