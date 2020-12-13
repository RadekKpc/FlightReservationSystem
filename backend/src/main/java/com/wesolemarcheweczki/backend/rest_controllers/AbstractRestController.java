package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.dao.GenericDao;
import com.wesolemarcheweczki.backend.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public abstract class AbstractRestController<T extends AbstractModel<T>> {

    @Autowired
    GenericDao<T> DAO;


    public ResponseEntity<T> get(@PathVariable("id") Integer id) {
        var dbResult = DAO.getById(id);
        if (dbResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dbResult.get(), HttpStatus.OK);
    }

    public ResponseEntity<List<T>> getAll() {
        try {
            return new ResponseEntity<>(DAO.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> create(@Valid @RequestBody T received) {
        try {
            if (DAO.add(received.copy())) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> update(@Valid @RequestBody T received) {
        try {
            if (DAO.update(received)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<T> updateToID(@Valid @RequestBody T received, @PathVariable("id") Integer id) {
        try {
            DAO.update(received, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
