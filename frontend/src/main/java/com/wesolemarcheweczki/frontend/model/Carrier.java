package com.wesolemarcheweczki.frontend.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Carrier {
    @Id
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
