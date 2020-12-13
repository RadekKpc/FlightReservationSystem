package com.wesolemarcheweczki.backend.rest_controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimpleController {

    @GetMapping("/check")
    public ResponseEntity<Void> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
