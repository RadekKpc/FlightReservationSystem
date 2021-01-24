package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Flight;

public class PriceSearchStrategy implements SearchStrategy {
    private final int price;


    public PriceSearchStrategy(int price) {
        this.price = price;
    }

    @Override
    public boolean filter(Flight f) {
        return f.getBaseCost() <= price;
    }
}
