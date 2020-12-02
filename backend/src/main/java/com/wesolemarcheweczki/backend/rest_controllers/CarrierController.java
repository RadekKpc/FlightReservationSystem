package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.model.Carrier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarrierController extends AbstractRestController<Carrier> {

    @Override
    @GetMapping(path = "/carrier/{id}")
    public ResponseEntity<Carrier> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Override
    @GetMapping(path = "/carrier")
    public ResponseEntity<List<Carrier>> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping(path = "/carrier", consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody Carrier carrier) {
        return super.create(carrier);
    }

    @Override
    @PutMapping(path = "/carrier", consumes = "application/json")
    //This one takes id from body, so breaks REST rules by a bit
    public ResponseEntity<Void> update(@Valid @RequestBody Carrier carrier) {
        return super.update(carrier);
    }

    @Override
    @PutMapping(path = "/carrier/{id}", consumes = "application/json") //This one takes id from URI
    public ResponseEntity<Carrier> updateToID(@Valid @RequestBody Carrier received, @PathVariable("id") Integer id) {
        return super.updateToID(received, id);
    }


}
