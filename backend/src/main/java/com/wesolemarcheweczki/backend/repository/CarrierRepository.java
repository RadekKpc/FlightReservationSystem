package com.wesolemarcheweczki.backend.repository;

import com.wesolemarcheweczki.backend.model.Carrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier,Integer> {
}
