package com.wesolemarcheweczki.backend.model;

import java.util.List;


public class Order {
    private final Client client;
    private final List<Ticket> tickets;


    public Order(Client client, List<Ticket> tickets) {
        this.client = client;
        this.tickets = tickets;
    }
}
