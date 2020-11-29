package com.wesolemarcheweczki.backend.model;


import javax.persistence.Embeddable;


@Embeddable
public class Passenger {

    private String firstName;
    private String lastName;

    public Passenger() {

    }

    public Passenger(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
