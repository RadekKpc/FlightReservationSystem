package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.model.Carrier;
import com.wesolemarcheweczki.backend.model.Flight;
import com.wesolemarcheweczki.backend.model.Order;
import com.wesolemarcheweczki.backend.model.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController extends AbstractRestController<Order> {

    @Override
    @GetMapping(path = "/order/{id}")
    public ResponseEntity<Order> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Override
    @GetMapping(path = "/order")
    public ResponseEntity<List<Order>> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping(path = "/order", consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody Order order) {
        return super.create(order);
    }

    @Override
    @PutMapping(path = "/order", consumes = "application/json")
    //This one takes id from body, so breaks REST rules by a bit
    public ResponseEntity<Void> update(@Valid @RequestBody Order order) {
        return super.update(order);
    }

    @Override
    @PutMapping(path = "/order/{id}", consumes = "application/json") //This one takes id from URI
    public ResponseEntity<Order> updateToID(@Valid @RequestBody Order received, @PathVariable("id") Integer id) {
        return super.updateToID(received, id);
    }


}
