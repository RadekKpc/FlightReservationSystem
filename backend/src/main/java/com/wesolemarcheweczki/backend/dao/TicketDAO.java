package com.wesolemarcheweczki.backend.dao;

import com.wesolemarcheweczki.backend.model.Order;
import com.wesolemarcheweczki.backend.model.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketDAO extends GenericDao<Ticket> {

    public List<Ticket> getForOrder(Order order) {
        return getFiltered(t -> t.getOrder().equals(order));
    }

    public List<Ticket> getForOrder(Integer id) {
        return getFiltered(t -> t.getOrder().getId() == id);
    }

    public List<Ticket> getForOrder(String email) { return getFiltered(t -> t.getOrder().getClient().getEmail().equals(email)); }

}
