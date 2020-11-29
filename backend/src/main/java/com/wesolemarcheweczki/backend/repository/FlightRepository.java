package com.wesolemarcheweczki.backend.repository;

import com.wesolemarcheweczki.backend.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {
}
