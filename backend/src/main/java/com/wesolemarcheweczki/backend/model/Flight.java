package com.wesolemarcheweczki.backend.model;

import java.time.LocalDateTime;


public class Flight {
    private final String flightCode;
    private final Carrier carrier;
    private final LocalDateTime departure;
    private final LocalDateTime arrival;
    private final int capacity;

//    private Collection<Ticket> tickets;

    public Flight(String flightCode, Carrier carrier, LocalDateTime departure, LocalDateTime arrival, int capacity) {
        this.flightCode = flightCode;
        this.carrier = carrier;
        this.departure = departure;
        this.arrival = arrival;
        this.capacity = capacity;
    }
}
