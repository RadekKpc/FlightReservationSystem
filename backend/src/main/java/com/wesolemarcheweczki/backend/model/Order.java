package com.wesolemarcheweczki.backend.model;

import javax.persistence.*;


@Entity
@Table(name = "Orders") //'"Order" is a keywor
public class Order implements AbstractModel<Order> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Client client;


    public Order(Client client) {
        this.client = client;
    }

    public Order() {
    }

    public Order(Order order) {
        this(order.client);
    }

    @Override
    public Order copy() {
        return new Order(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setParams(Order object) {
        client = object.client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

