package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.dao.CarrierDAO;
import com.wesolemarcheweczki.backend.model.Carrier;
import com.wesolemarcheweczki.backend.model.CarrierStats;
import com.wesolemarcheweczki.backend.rest_controllers.helpers.body_mappers.CarrierStatsBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.wesolemarcheweczki.backend.rest_controllers.helpers.Responses.*;

@RestController
@RequestMapping("/api/carrier")
public class CarrierController extends AbstractRestController<Carrier> {

    @Autowired
    public CarrierController() {
        super();
    }

    @GetMapping(path = "/stats", consumes = "application/json")
    public ResponseEntity<List<CarrierStats>> getCarrierStats(@RequestBody CarrierStatsBody carrierBody){
        List<CarrierStats> ret = ((CarrierDAO)DAO).getCarrierStatsInRange(carrierBody.getFrom(), carrierBody.getTo());
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}