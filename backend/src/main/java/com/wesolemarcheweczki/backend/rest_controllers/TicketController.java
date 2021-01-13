package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.dao.OrderDAO;
import com.wesolemarcheweczki.backend.dao.TicketDAO;
import com.wesolemarcheweczki.backend.model.Order;
import com.wesolemarcheweczki.backend.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wesolemarcheweczki.backend.rest_controllers.helpers.Responses.internalServerError;

@RestController
@RequestMapping("/api/ticket")
public class TicketController extends AbstractRestController<Ticket> {

    @Autowired
    public TicketController() {
        super();
    }

    @GetMapping(path = "/order", consumes = "application/json")
    public ResponseEntity<List<Ticket>> getForClient(@Valid @RequestBody Order received) {
        var dao = (TicketDAO) DAO;
        try {
            return new ResponseEntity<>(dao.getForOrder(received), HttpStatus.OK);
        } catch (Exception e) {
            return internalServerError();
        }
    }

    @GetMapping(path = "/order/{id}")
    public ResponseEntity<List<Ticket>> getForClient(@PathVariable("id") Integer id) {
        var dao = (TicketDAO) DAO;
        try {
            return new ResponseEntity<>(dao.getForOrder(id), HttpStatus.OK);
        } catch (Exception e) {
            return internalServerError();
        }
    }

    @GetMapping(path = "/email/{email}")
    public ResponseEntity<List<Ticket>> getForClientWithEmailBody(@PathVariable("email") String email) {
        var dao = (TicketDAO) DAO;
        try {
            return new ResponseEntity<>(dao.getForOrder(email), HttpStatus.OK);
        } catch (Exception e) {
            return internalServerError();
        }
    }

}
