package com.wesolemarcheweczki.backend.model;

import javax.persistence.*;

@Entity
public class Carrier {
    @Id
    @Column(name = "CARRIER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
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
