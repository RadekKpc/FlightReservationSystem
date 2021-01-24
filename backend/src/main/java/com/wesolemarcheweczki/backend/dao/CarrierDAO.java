package com.wesolemarcheweczki.backend.dao;

import com.wesolemarcheweczki.backend.model.Carrier;
import com.wesolemarcheweczki.backend.model.CarrierStats;
import com.wesolemarcheweczki.backend.repository.CarrierRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CarrierDAO extends GenericDao<Carrier> {

    public int getCarrierTotalFlightsCost(int carrierId){
        return ((CarrierRepository) repository).getCarrierTotalFlightsCost(carrierId);
    }


    public int getCarrierTotalFlightsAmount(int carrierId){
        return ((CarrierRepository) repository).getCarrierTotalFlightsAmount(carrierId);
    }

    public List<CarrierStats> getCarrierStatsInRange(LocalDateTime from, LocalDateTime to){
        var stats = ((CarrierRepository)repository).getCarrierStatsInRange(from, to);
        if(stats == null){
            return new ArrayList<>();
        }
        stats.forEach(stat -> {
            stat.setFrom(from);
            stat.setTo(to);
        });
        return stats;
    }

    /*public Object getCarrierTopFlightLocations(Carrier carrier){
        return ((CarrierRepository) repository).getCarrierTopFlightLocations(carrier);
    }
     */

}
