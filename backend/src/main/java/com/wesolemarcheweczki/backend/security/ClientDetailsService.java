package com.wesolemarcheweczki.backend.security;

import com.wesolemarcheweczki.backend.dao.ClientDAO;
import com.wesolemarcheweczki.backend.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private ClientDAO clientDAO;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Client user = clientDAO.getByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new ClientDetails(user);
    }
}