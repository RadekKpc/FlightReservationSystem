package com.wesolemarcheweczki.backend.dao;

import com.wesolemarcheweczki.backend.model.Carrier;
import com.wesolemarcheweczki.backend.model.CarrierStats;
import com.wesolemarcheweczki.backend.repository.CarrierRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CarrierDAO extends GenericDao<Carrier> {

    public int getCarrierTotalFlightsCost(int carrierId){
        return ((CarrierRepository) repository).getCarrierTotalFlightsCost(carrierId);
    }


    public int getCarrierTotalFlightsAmount(int carrierId){
        return ((CarrierRepository) repository).getCarrierTotalFlightsAmount(carrierId);
    }

    public CarrierStats getCarrierStatsInRange(int carrierId, LocalDateTime from, LocalDateTime to){
        var stats = ((CarrierRepository)repository).getCarrierStatsInRange(carrierId, from, to);
        if(stats == null){
            return null;
        }
        stats.setFrom(from);
        stats.setTo(to);
        return stats;
    }

    /*public Object getCarrierTopFlightLocations(Carrier carrier){
        return ((CarrierRepository) repository).getCarrierTopFlightLocations(carrier);
    }
     */

}
