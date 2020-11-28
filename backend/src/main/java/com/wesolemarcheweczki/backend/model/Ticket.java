package com.wesolemarcheweczki.backend.model;


public class Ticket {
    private final Passenger passenger;
    private final Flight flight;
    private final int seat;
    private final int cost;
//    private boolean paid;

    public Ticket(Passenger passenger, Flight flight, int seat, int cost) {
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
        this.cost = cost;
    }
}
