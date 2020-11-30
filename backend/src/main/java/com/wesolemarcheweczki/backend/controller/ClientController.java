package com.wesolemarcheweczki.backend.controller;

import com.wesolemarcheweczki.backend.dao.ClientDAO;
import com.wesolemarcheweczki.backend.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientDAO clientDAO;

    @PostMapping(path = "/client",consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> createClient(@Valid @RequestBody Client client){
        try {

            clientDAO.save(client);
            return ResponseEntity.ok(" \"added\" : \"true\"");
        }
        catch (Exception e){
            return ResponseEntity.ok(" \"added\" : \"false\"");
        }
    }

    @GetMapping(path = "/client/{id}")
    public @ResponseBody Client getClient(@PathVariable("id") Integer id){
        try {
            return clientDAO.getById(id);
        }
        catch (NoSuchElementException e){
            return null;
        }
    }

}
