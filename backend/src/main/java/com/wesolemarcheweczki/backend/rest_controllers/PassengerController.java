package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PassengerController extends AbstractRestController<Passenger> {

    @Override
    @GetMapping(path = "/passenger/{id}")
    public ResponseEntity<Passenger> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Override
    @GetMapping(path = "/passenger")
    public ResponseEntity<List<Passenger>> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping(path = "/passenger", consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody Passenger passenger) {
        return super.create(passenger);
    }

    @Override
    @PutMapping(path = "/passenger", consumes = "application/json")
    //This one takes id from body, so breaks REST rules by a bit
    public ResponseEntity<Void> update(@Valid @RequestBody Passenger passenger) {
        return super.update(passenger);
    }

    @Override
    @PutMapping(path = "/passenger/{id}", consumes = "application/json") //This one takes id from URI
    public ResponseEntity<Passenger> updateToID(@Valid @RequestBody Passenger received, @PathVariable("id") Integer id) {
        return super.updateToID(received, id);
    }


}
