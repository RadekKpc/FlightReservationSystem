package com.wesolemarcheweczki.frontend.model;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Client {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String firstName;

    private String lastName;

    private String email;

    private String password;
    private int id;

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = encoder.encode(password);
    }

    public Client() {}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {return password;}

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = encoder.encode(password);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
