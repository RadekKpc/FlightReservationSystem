package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.model.Carrier;
import com.wesolemarcheweczki.backend.model.Flight;
import com.wesolemarcheweczki.backend.model.Ticket;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController extends AbstractRestController<Ticket> {

    @Override
    @GetMapping(path = "/ticket/{id}")
    public ResponseEntity<Ticket> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Override
    @GetMapping(path = "/ticket")
    public ResponseEntity<List<Ticket>> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping(path = "/ticket", consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody Ticket ticket) {
        return super.create(ticket);
    }

    @Override
    @PutMapping(path = "/ticket", consumes = "application/json")
    //This one takes id from body, so breaks REST rules by a bit
    public ResponseEntity<Void> update(@Valid @RequestBody Ticket ticket) {
        return super.update(ticket);
    }

    @Override
    @PutMapping(path = "/ticket/{id}", consumes = "application/json") //This one takes id from URI
    public ResponseEntity<Ticket> updateToID(@Valid @RequestBody Ticket received, @PathVariable("id") Integer id) {
        return super.updateToID(received, id);
    }


}
