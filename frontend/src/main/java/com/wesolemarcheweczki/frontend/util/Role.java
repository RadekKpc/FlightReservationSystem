package com.wesolemarcheweczki.frontend.util;

public enum Role {
    USER,ADMIN;

    public static String asText(Role role){
        if(role == USER){
            return "ROLE_USER";
        }
        if(role == ADMIN){
            return "ROLE_ADMIN";
        }
        return null;
    }
}
