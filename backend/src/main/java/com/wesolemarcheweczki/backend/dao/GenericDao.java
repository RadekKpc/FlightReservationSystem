package com.wesolemarcheweczki.backend.dao;


import com.wesolemarcheweczki.backend.model.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// I believe this class could be used via composition instead of inheritance, but I failed to fix failing IOC for repository field

@Component
public abstract class GenericDao<T extends AbstractModel<T>> {

    @Autowired
    private JpaRepository<T, Integer> repository;

    public boolean add(T object) {
        try {
            repository.save(object);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean addAll(Iterable<T> objects) { //for some reason List here breaks app launch, worth investigating
        try {
            repository.saveAll(objects);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(T object) {
        return update(object, object.getId());
    }

    public boolean update(T object, Integer id) {
        var found = repository.findById(id);
        if (found.isEmpty()) {
            return false;
        }
        found.get().setParams(object);
        repository.save(found.get());
        return true;
    }

    public boolean updateAll(Iterable<T> objects) {
        return getStream(objects)
                .map(this::update)
                .reduce(true, (a, b) -> a && b); //will return false if any fails
    }

    public boolean delete(T object) {
        try {
            repository.delete(object);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Integer id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Optional<T> getById(Integer id) {
        return repository.findById(id);
    }

    public List<T> getAll() {
        return repository.findAll();
    }

    protected Stream<T> getStream(Iterable<T> objects) {
        return StreamSupport.stream(objects.spliterator(), false);
    }

    protected List<T> getFiltered(Predicate<T> predicate) {
        return getAll().stream().filter(predicate).collect(Collectors.toList());
    }

}
