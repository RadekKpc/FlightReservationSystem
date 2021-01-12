package com.wesolemarcheweczki.frontend.model;

public class Order {

    private int id;
    private Client client;

    public Order(Client client) {
        this.client = client;
    }

    public Order() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

