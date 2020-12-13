package com.wesolemarcheweczki.backend.security.helpers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Simple script that encrypts plaintext password, helpful when manually adding to DB

public class SecuredPasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "test";
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println(encodedPassword);
    }

}