package com.wesolemarcheweczki.frontend.model;

public class Location {

    private String airportId;
    private String city;
    private String country;

    public Location(){}

    public Location(String airportId, String city, String country) {
        this.airportId = airportId;
        this.city = city;
        this.country = country;
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
