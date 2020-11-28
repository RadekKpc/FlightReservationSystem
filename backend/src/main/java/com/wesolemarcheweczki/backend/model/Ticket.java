package com.wesolemarcheweczki.backend.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @Column(name = "TICKET_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    private Passenger passenger;

    @OneToOne
    private Flight flight;

    private int seat;

    private int cost;
//    private boolean paid;

    public Ticket(){
    }

    public Ticket(Passenger passenger, Flight flight, int seat, int cost) {
        this.passenger = passenger;
        this.flight = flight;
        this.seat = seat;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
