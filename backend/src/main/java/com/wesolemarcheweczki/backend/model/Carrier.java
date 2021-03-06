package com.wesolemarcheweczki.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Carrier implements AbstractModel<Carrier> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    public Carrier() {
    }

    public Carrier(String name) {
        this.name = name;
    }

    public Carrier(Carrier carrier) {
        this(carrier.name);
    }

    @Override
    public Carrier copy() {
        return new Carrier(this);
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

    @Override
    public void setParams(Carrier object) {
        name = object.getName();
    }
}
