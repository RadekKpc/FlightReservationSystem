package com.wesolemarcheweczki.backend.dao;

import com.wesolemarcheweczki.backend.model.Client;
import com.wesolemarcheweczki.backend.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDAO extends GenericDao<Order> {

    public List<Order> getForClient(Client client) {
        return getFiltered(o -> o.getClient().equals(client));
    }

    public List<Order> getForClient(Integer id) {
        return getFiltered(o -> o.getClient().getId() == id);
    }
}
