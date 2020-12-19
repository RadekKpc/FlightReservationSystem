package com.wesolemarcheweczki.backend.model;

import javax.persistence.*;

@Entity
public class Ticket implements AbstractModel<Ticket> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Embedded
    private Passenger passenger;

    @ManyToOne
    private Flight flight;

    @ManyToOne
    private Order order;

    private int seat;

    private int cost;
//    private boolean paid;

    public Ticket() {
    }

    public Ticket(Passenger passenger, Flight flight, Order order, int seat, int cost) {
        this.passenger = passenger;
        this.flight = flight;
        this.order = order;
        this.seat = seat;
        this.cost = cost;
    }

    public Ticket(Ticket ticket) {
        this(ticket.passenger, ticket.flight, ticket.order, ticket.seat, ticket.cost);
    }

    @Override
    public Ticket copy() {
        return new Ticket(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setParams(Ticket object) {
        passenger = object.passenger;
        flight = object.flight;
        order = object.order;
        seat = object.seat;
        cost = object.cost;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
