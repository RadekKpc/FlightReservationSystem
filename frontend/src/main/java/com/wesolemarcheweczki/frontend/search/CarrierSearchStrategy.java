package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Carrier;
import com.wesolemarcheweczki.frontend.model.Flight;

public class CarrierSearchStrategy implements SearchStrategy {
    private final Carrier carrier;

    public CarrierSearchStrategy(Carrier carrier) {
        this.carrier = carrier;
    }

    @Override
    public boolean filter(Flight f) {
        return f.getCarrier().equals(this.carrier);
    }
}
