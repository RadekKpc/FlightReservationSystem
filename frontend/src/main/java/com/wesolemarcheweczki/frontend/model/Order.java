package com.wesolemarcheweczki.frontend.model;

public class Order {

    private int id;
    private String clientEmail;

    public Order(String client) {
        this.clientEmail = client;
    }

    public Order() {
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(Client client) {
        this.clientEmail = clientEmail;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

