package com.wesolemarcheweczki.backend.dao;

import com.wesolemarcheweczki.backend.model.Client;
import com.wesolemarcheweczki.backend.model.Flight;
import com.wesolemarcheweczki.backend.repository.FlightRepository;
import com.wesolemarcheweczki.backend.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightDAO extends GenericDao<Flight> {

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public List<Flight> getAll() {
        List<Flight> flights = repository.findAll();

        flights.forEach(f -> f.setFreePlaces(getFreePlaces(f)));
        return flights;
    }

    public int getFreePlaces(Flight flight){
        return flight.getCapacity() - ticketRepository.getCountOfTicketsForFlight(flight);
    }

    public boolean getCollision(Flight flight, Client client){
        System.out.println(((FlightRepository) repository).getCollisionWithFlightForGivenClient(flight.getDeparture(),flight.getArrival(),client));
        return ((FlightRepository) repository).getCollisionWithFlightForGivenClient(flight.getDeparture(),flight.getArrival(),client) == 0;
    }

}
