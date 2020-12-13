package com.wesolemarcheweczki.backend.dao;

import com.wesolemarcheweczki.backend.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientDAO extends GenericDao<Client> {

    public Client getByEmail(String email) {
        return getAll().stream().filter(u -> u.getEmail().equals(email)).findAny().orElse(null);
    }

    @Override
    public boolean add(Client object) {
        if (clientExists(object)) {
            return false;
        }
        return super.add(object);
    }

    @Override
    public boolean addAll(Iterable<Client> objects) {
        if (getStream(objects).anyMatch(this::clientExists)) {
            return false;
        }
        return super.addAll(objects);
    }

    private boolean clientExists(Client object) {
        return getByEmail(object.getEmail()) != null;
    }
}
