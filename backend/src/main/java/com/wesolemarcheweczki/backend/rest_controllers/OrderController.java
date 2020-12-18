package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.dao.OrderDAO;
import com.wesolemarcheweczki.backend.model.Client;
import com.wesolemarcheweczki.backend.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wesolemarcheweczki.backend.rest_controllers.helpers.Responses.internalServerError;

@RestController
@RequestMapping("/api/order")
public class OrderController extends AbstractRestController<Order> {

    @Autowired
    public OrderController() {
        super();
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

}
