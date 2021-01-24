package com.wesolemarcheweczki.backend.rest_controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wesolemarcheweczki.backend.dao.ClientDAO;
import com.wesolemarcheweczki.backend.dao.FlightDAO;
import com.wesolemarcheweczki.backend.dao.OrderDAO;
import com.wesolemarcheweczki.backend.model.Client;
import com.wesolemarcheweczki.backend.model.Flight;
import com.wesolemarcheweczki.backend.model.Order;
import com.wesolemarcheweczki.backend.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.wesolemarcheweczki.backend.rest_controllers.helpers.Responses.*;

@RestController
@RequestMapping("/api/flight")
public class FlightController extends AbstractRestController<Flight> {

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    public FlightController() {
        super();
    }

    @GetMapping(path = "/collision", consumes = "application/json")
    public ResponseEntity<Boolean> getCollision(@RequestBody ObjectNode clientEmailAndFlight) {

        try {

            Client client = clientDAO.getByEmail(clientEmailAndFlight.get("email").asText());
            Flight flight = DAO.getById(clientEmailAndFlight.get("flightId").asInt()).orElse(null);

            if(client == null || flight == null){
                return forbidden();
            }

            if(((FlightDAO) DAO).getCollision(flight,client)){
                return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
            }

        } catch (Exception e) {
            return badRequest();
        }
    }
}
