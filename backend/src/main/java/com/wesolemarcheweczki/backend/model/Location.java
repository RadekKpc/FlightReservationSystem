package com.wesolemarcheweczki.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location implements AbstractModel<Location>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String airportId;
    private String city;
    private String country;

    public Location() {

    }

    public Location(Location location) {
        this(location.airportId, location.city, location.country);
    }

    public Location(String airportId, String city, String country) {
        this.airportId = airportId;
        this.city = city;
        this.country = country;
    }

    @Override
    public Location copy() {
        return new Location(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setParams(Location object) {
        airportId = object.getAirportId();
        city = object.getCity();
        country = object.getCountry();
    }

    public String getAirportId() {
        return airportId;
    }

    public void setAirportId(String airportId) {
        this.airportId = airportId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
