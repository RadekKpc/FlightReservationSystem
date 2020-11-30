package com.wesolemarcheweczki.frontend.model;



public class Carrier {

    private int id;

    private String name;

    public Carrier() {
    }

    public Carrier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
