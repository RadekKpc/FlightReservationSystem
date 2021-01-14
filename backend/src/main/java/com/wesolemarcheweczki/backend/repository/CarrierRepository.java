package com.wesolemarcheweczki.backend.repository;

import com.wesolemarcheweczki.backend.model.Carrier;
import com.wesolemarcheweczki.backend.model.CarrierStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.wesolemarcheweczki.backend.model.CarrierStats;
import java.time.LocalDateTime;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier,Integer> {
    @Query("SELECT SUM(t.cost) FROM Ticket AS t " +
            "JOIN Flight f on t.flight = f " +
            "WHERE f.carrier.id = ?1 ")
    int getCarrierTotalFlightsCost(int carrierId);

    @Query("SELECT COUNT(*) FROM Flight as f " +
            "WHERE f.carrier.id = ?1 ")
    int getCarrierTotalFlightsAmount(int carrierId);

    @Query(value = "SELECT new com.wesolemarcheweczki.backend.model.CarrierStats(c.id, COUNT(f.id), SUM(t.cost))" +
            " FROM Carrier c\n" +
            "JOIN Flight f on f.carrier.id = c.id and f.departure >= ?2 and f.arrival <= ?3\n" +
            "JOIN Ticket t on t.flight = f \n" +
            "GROUP BY c.id HAVING c.id = ?1\n")
    CarrierStats getCarrierStatsInRange(int carrierId, LocalDateTime from, LocalDateTime to);


    /*@Query("SELECT f.source, f.destination FROM flight f " +
            "JOIN ticket t on t.flight = f " +
            "GROUP BY f.source, f.destination HAVING f.carrier = ?1 " +
            "ORDER BY COUNT(*) DESC " +
            "LIMIT 1 ")
    Object getCarrierTopFlightLocations(Carrier carrier);
     */
}
