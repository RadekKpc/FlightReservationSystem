package com.wesolemarcheweczki.frontend.util;

public class AuthManager {
    public static String pwd;
    public static String email;

    public static void setPwd(String pwd) {
        AuthManager.pwd = pwd;
    }

    public static void setEmail(String email) {
        AuthManager.email = email;
    }
}
