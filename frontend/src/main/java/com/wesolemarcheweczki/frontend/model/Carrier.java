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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
