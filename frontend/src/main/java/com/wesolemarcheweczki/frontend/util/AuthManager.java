package com.wesolemarcheweczki.frontend.util;

import static com.wesolemarcheweczki.frontend.util.Role.ADMIN;
import static com.wesolemarcheweczki.frontend.util.Role.USER;

public class AuthManager {
    public static String pwd;
    public static String email;
    public static Role role = USER;

    public static void setPwd(String pwd) {
        AuthManager.pwd = pwd;
    }

    public static void setEmail(String email) {
        AuthManager.email = email;
    }

    public static void setRole(Role role) {
        AuthManager.role = role;
    }
    public static void setRole(String role) {
        if(role.equals("ADMIN") || role.equals("ROLE_ADMIN") ) {
            AuthManager.role = ADMIN;
        }
        if(role.equals("USER") || role.equals("ROLE_USER")){
            AuthManager.role = USER;
        }
    }

    public static boolean isAdmin(){
        return role == ADMIN;
    }
}
