package com.wesolemarcheweczki.frontend.model;


public class Ticket {
    private Passenger passenger;

    private Flight flight;

    private Order order;

    private int seat;

    private int cost;
       private int id;
//    private boolean paid;

    public Ticket() {
    }

    public Ticket(int id, Passenger passenger, Flight f, Order o, int seat, int cost) {
        this.id = id;
        this.passenger = passenger;
        this.flight = f;
        this.order = o;
        this.seat = seat;
        this.cost = cost;
    }

    public Ticket(Passenger passenger, int seat, int cost, Flight f, Order o) {
        this.passenger = passenger;
        this.flight = f;
        this.order = o;
        this.seat = seat;
        this.cost = cost;
    }

    public Ticket(Passenger passenger, int seat, int cost) {
        this.passenger = passenger;
//        this.flight = flight;
//        this.order = order;s
        this.seat = seat;
        this.cost = cost;
    }
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
