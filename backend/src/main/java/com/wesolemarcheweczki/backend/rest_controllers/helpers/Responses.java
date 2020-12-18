package com.wesolemarcheweczki.backend.rest_controllers.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Responses {
    public static final <U> ResponseEntity<U> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static final <U> ResponseEntity<U> badRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public static final <U> ResponseEntity<U> forbidden() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    public static final <U> ResponseEntity<U> internalServerError() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
