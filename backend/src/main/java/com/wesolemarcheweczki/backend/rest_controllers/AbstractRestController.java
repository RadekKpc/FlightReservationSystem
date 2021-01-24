package com.wesolemarcheweczki.backend.rest_controllers;

import com.wesolemarcheweczki.backend.dao.GenericDao;
import com.wesolemarcheweczki.backend.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wesolemarcheweczki.backend.rest_controllers.helpers.Responses.*;

public abstract class AbstractRestController<T extends AbstractModel<T>> {

    @Autowired
    protected GenericDao<T> DAO;

    @GetMapping(path = "/{id}")
    public ResponseEntity<T> get(@PathVariable("id") Integer id) {
        var dbResult = DAO.getById(id);
        if (dbResult.isEmpty()) {
            return badRequest();
        }
        return new ResponseEntity<>(dbResult.get(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<T>> getAll() {
        try {
            return new ResponseEntity<>(DAO.getAll(), HttpStatus.OK);
        } catch (Exception e) {
            return internalServerError();
        }
    }

    @PostMapping(consumes = "application/json") //This one always creates new instance
    public ResponseEntity<Void> create(@Valid @RequestBody T received) {
        try {
            if (DAO.add(received.copy())) {
                return ok();
            }
            return forbidden();
        } catch (Exception e) {
            return badRequest();
        }
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<Void> update(@Valid @RequestBody T received) {
        try {
            if (DAO.update(received)) {
                return ok();
            }
            return forbidden();

        } catch (Exception e) {
            return badRequest();
        }
    }

    @PutMapping(path = "/{id}", consumes = "application/json") //This one takes id from URI
    public ResponseEntity<Void> updateToID(@Valid @RequestBody T received, @PathVariable("id") Integer id) {
        try {
            DAO.update(received, id);
            return ok();
        } catch (Exception e) {
            return badRequest();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@Valid @RequestBody T object) {
        boolean success = DAO.delete(object);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return badRequest();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteID(@PathVariable("id") Integer id) {
        boolean success = DAO.delete(id);
        if (success) {
            return ok();
        }
        return badRequest();
    }


}
