package com.wesolemarcheweczki.backend.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;


public abstract class GenericDao<T, S> {

    @Autowired
    private JpaRepository<T, S> repository;

    public void save(T object) {
        repository.save(object);
    }

    public void saveAll(Iterable<T> object) {
        repository.saveAll(object);
    }

    public T getById(S id){
        return repository.findById(id).get();
    }

}
