package com.wesolemarcheweczki.backend.model;

public interface AbstractModel<T extends AbstractModel<T>> {

    T copy();

    int getId();

    void setId(int id);

    void setParams(T object);
}
