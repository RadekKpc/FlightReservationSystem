package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.model.Carrier;
import com.wesolemarcheweczki.backend.model.Flight;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FlightController extends AbstractRestController<Flight> {

    @Override
    @GetMapping(path = "/flight/{id}")
    public ResponseEntity<Flight> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Override
    @GetMapping(path = "/flight")
    public ResponseEntity<List<Flight>> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping(path = "/flight", consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody Flight flight) {
        return super.create(flight);
    }

    @Override
    @PutMapping(path = "/flight", consumes = "application/json")
    //This one takes id from body, so breaks REST rules by a bit
    public ResponseEntity<Void> update(@Valid @RequestBody Flight flight) {
        return super.update(flight);
    }

    @Override
    @PutMapping(path = "/flight/{id}", consumes = "application/json") //This one takes id from URI
    public ResponseEntity<Flight> updateToID(@Valid @RequestBody Flight received, @PathVariable("id") Integer id) {
        return super.updateToID(received, id);
    }


}
