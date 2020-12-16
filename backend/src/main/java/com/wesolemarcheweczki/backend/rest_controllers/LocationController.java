package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.model.Client;
import com.wesolemarcheweczki.backend.model.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController extends AbstractRestController<Location> {

    @Override
    @GetMapping(path = "/location/{id}")
    public ResponseEntity<Location> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Override
    @GetMapping(path = "/location")
    public ResponseEntity<List<Location>> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping(path = "/location", consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody Location location) {
        return super.create(location);
    }

    @Override
    @PutMapping(path = "/location", consumes = "application/json")
    //This one takes id from body, so breaks REST rules by a bit
    public ResponseEntity<Void> update(@Valid @RequestBody Location location) {
        return super.update(location);
    }

    @Override
    @PutMapping(path = "/location/{id}", consumes = "application/json") //This one takes id from URI
    public ResponseEntity<Location> updateToID(@Valid @RequestBody Location received, @PathVariable("id") Integer id) {
        return super.updateToID(received, id);
    }


}
