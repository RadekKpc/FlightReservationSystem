package com.wesolemarcheweczki.backend.repository;

import com.wesolemarcheweczki.backend.model.Flight;
import com.wesolemarcheweczki.backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    @Query("SELECT COUNT(*) FROM Ticket AS t WHERE t.flight = ?1")
    int getCountOfTicketsForFlight(Flight flight);
}
