package com.wesolemarcheweczki.frontend.model;


public class Ticket {
    private Passenger passenger;

    //private Flight flight;

    //private Order order;

    private int seat;

    private int cost;
    //   private int id;
//    private boolean paid;

    public Ticket() {
    }

    public Ticket(Passenger passenger, int seat, int cost) {
        this.passenger = passenger;
//        this.flight = flight;
//        this.order = order;
        this.seat = seat;
        this.cost = cost;
    }
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

/*    public Flight getFlight() {
        return flight;
    }*/

    /*public void setFlight(Flight flight) {
        this.flight = flight;
    }*/

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
    /*public void setId(int id) {
        this.id = id;
    }*/

    /*public int getId() {
        return id;
    }*/
}
