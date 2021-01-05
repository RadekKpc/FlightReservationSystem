package com.wesolemarcheweczki.backend.dao;

import com.wesolemarcheweczki.backend.model.Flight;
import com.wesolemarcheweczki.backend.model.Ticket;
import com.wesolemarcheweczki.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightDAO extends GenericDao<Flight> {

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<Flight> getAll() {
        List<Flight> flights = repository.findAll();
        for(Flight flight: flights){
            flight.setFreePlaces(getFreePlaces(flight));
        }
        return flights;
    }

    public int getFreePlaces(Flight flight){
        return flight.getCapacity() - ticketRepository.getCountOfTicketsForFlight(flight);
    }

}
