package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Flight;

public class PlaceSearchStrategy implements ISearchStrategy{
    private String city;
    private String country;
    private boolean from;


    public PlaceSearchStrategy(String city, String country, boolean from) {
        this.city = city;
        this.country = country;
        this.from = from;
    }

    @Override
    public boolean filter(Flight f) {
        return from
                ? (f.getSource().getCountry().equals(this.country) && f.getSource().getCity().equals(this.city))
                : (f.getDestination().getCountry().equals(this.country) && f.getDestination().getCity().equals(this.city));
    }
}
