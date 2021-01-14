package com.wesolemarcheweczki.backend.repository;

import com.wesolemarcheweczki.backend.model.Client;
import com.wesolemarcheweczki.backend.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {
    @Query("SELECT COUNT(*) FROM Order AS o " +
            "JOIN Ticket t on t.order = o " +
            "JOIN Flight f on t.flight = f " +
            "WHERE o.client = ?3 AND " +
            "((f.arrival >= ?1 AND f.arrival <= ?2)" +
            "OR (f.departure >= ?1 AND f.departure <= ?2))")
    int getCollisionWithFlightForGivenClient(LocalDateTime departure, LocalDateTime arrival, Client client);
}
