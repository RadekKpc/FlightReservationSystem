package com.wesolemarcheweczki.backend.rest_controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wesolemarcheweczki.backend.dao.ClientDAO;
import com.wesolemarcheweczki.backend.dao.FlightDAO;
import com.wesolemarcheweczki.backend.dao.OrderDAO;
import com.wesolemarcheweczki.backend.dao.TicketDAO;
import com.wesolemarcheweczki.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wesolemarcheweczki.backend.rest_controllers.helpers.Responses.*;
import static com.wesolemarcheweczki.backend.rest_controllers.helpers.Responses.badRequest;

@RestController
@RequestMapping("/api/order")
public class OrderController extends AbstractRestController<Order> {

    @Autowired
    ClientDAO clientDAO;

    @Autowired
    TicketDAO ticketDAO;

    @Autowired
    FlightDAO flightDAO;

    @Autowired
    public OrderController() {
        super();
    }


//    JSON FORMAT FOR BELOW POST
//{
//    "email": "test@test.com",
//        "flightId": "10",
//        "tickets": [
//    {
//        "passenger": {
//        "firstName": "Janusz",
//                "lastName": "Nowak"
//    },
//        "seat": 10,
//            "cost": 1000
//    }
//    ]
//}
//    need to add transactiona nd check has client reserve flight at the same time
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE) //This one always creates new instance
    public ResponseEntity<Void> create(@RequestBody ObjectNode emailAndTickets) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader reader = objectMapper.readerFor(new TypeReference<List<Ticket>>() {
            });

            Client client = clientDAO.getByEmail(emailAndTickets.get("email").asText());
            Flight flight = flightDAO.getById(emailAndTickets.get("flightId").asInt()).orElse(null);
            List<Ticket> tickets = reader.readValue(emailAndTickets.get("tickets"));



            Order order = new Order();
            order.setClient(client);
            DAO.add(order);

            for(Ticket t: tickets){
                t.setOrder(order);
                t.setFlight(flight);
                ticketDAO.add(t);

            }
//            if (DAO.add(order)) {
            return ok();

//            }
//            return forbidden();
        } catch (Exception e) {
            return badRequest();
        }
    }

    @GetMapping(path = "/client", consumes = "application/json")
    public ResponseEntity<List<Order>> getForClient(@Valid @RequestBody Client received) {
        var dao = (OrderDAO) DAO;
        try {
            return new ResponseEntity<>(dao.getForClient(received), HttpStatus.OK);
        } catch (Exception e) {
            return internalServerError();
        }
    }

    @GetMapping(path = "/client/{id}")
    public ResponseEntity<List<Order>> getForClient(@PathVariable("id") Integer id) {
        var dao = (OrderDAO) DAO;
        try {
            return new ResponseEntity<>(dao.getForClient(id), HttpStatus.OK);
        } catch (Exception e) {
            return internalServerError();
        }
    }

    @GetMapping(path = "/client/email/{email}")
    public ResponseEntity<List<Order>> getForClient(@PathVariable("email") String email) {
        var dao = (OrderDAO) DAO;
        try {
            return new ResponseEntity<>(dao.getForClient(email), HttpStatus.OK);
        } catch (Exception e) {
            return internalServerError();
        }
    }

    @GetMapping(path = "/client/email", consumes = "text/plain")
    public ResponseEntity<List<Order>> getForClientWithEmailBody(@RequestBody String email) {
        var dao = (OrderDAO) DAO;
        try {
            return new ResponseEntity<>(dao.getForClient(email), HttpStatus.OK);
        } catch (Exception e) {
            return internalServerError();
        }
    }

}
