package com.wesolemarcheweczki.frontend.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Carrier {

    private int id;
    private StringProperty name;

    public String toString(){
        return getName();
    }

    @JsonCreator
    public Carrier(@JsonProperty("name") String name, @JsonProperty("id") int id) {
        this.name = new SimpleStringProperty(name);
        this.id = id;
    }

    public Carrier(String name){
        this.name = new SimpleStringProperty(name);
    }

    public Carrier() {
        this.name = new SimpleStringProperty("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObjectProperty<Carrier> carrierProperty() {
        return new SimpleObjectProperty<>(this);
    }
}
