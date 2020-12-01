package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.model.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController extends AbstractRestController<Client> {

    @Override
    @GetMapping(path = "/client/{id}")
    public ResponseEntity<Client> get(@PathVariable("id") Integer id) {
        return super.get(id);
    }

    @Override
    @GetMapping(path = "/client")
    public ResponseEntity<List<Client>> getAll() {
        return super.getAll();
    }

    @Override
    @PostMapping(path = "/client", consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody Client client) {
        return super.create(client);
    }

    @Override
    @PutMapping(path = "/client", consumes = "application/json")
    //This one takes id from body, so breaks REST rules by a bit
    public ResponseEntity<Void> update(@Valid @RequestBody Client client) {
        return super.update(client);
    }

    @Override
    @PutMapping(path = "/client/{id}", consumes = "application/json") //This one takes id from URI
    public ResponseEntity<Client> updateToID(@Valid @RequestBody Client received, @PathVariable("id") Integer id) {
        return super.updateToID(received, id);
    }


}
