package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.dao.ClientDAO;
import com.wesolemarcheweczki.backend.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.wesolemarcheweczki.backend.rest_controllers.helpers.Responses.*;

@RestController
@RequestMapping("/api/client")
public class ClientController extends AbstractRestController<Client> {

    @Autowired
    public ClientController() {
        super();
    }

    @Override
    @PostMapping(consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody Client received) {
        try {
            received.setRole("ROLE_USER");

            if (DAO.add(received.copy())) {
                return ok();
            }
            return forbidden();
        } catch (Exception e) {
            return badRequest();
        }
    }

    @GetMapping("/role")
    public ResponseEntity<String> checkRole(@RequestHeader("email") String email) {
        try {
            return new ResponseEntity<>(((ClientDAO) DAO).getRole(email), HttpStatus.OK);
        } catch (Exception e) {
            return badRequest();
        }
    }

    @PostMapping(path = "/admin", consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> createWithRole(@Valid @RequestBody Client received) {
        try {
            if (DAO.add(received.copy())) {
                return ok();
            }
            return forbidden();
        } catch (Exception e) {
            return badRequest();
        }
    }

}
