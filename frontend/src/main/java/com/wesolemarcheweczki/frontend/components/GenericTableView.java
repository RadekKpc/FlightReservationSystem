package com.wesolemarcheweczki.frontend.components;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Arrays;
import java.util.List;

public class GenericTableView<S> extends TableView {

    final Class<S> typeParameterClass;

    public GenericTableView(ObservableList data, List<String> columnNames, Class<S> typeParameterClass){
        super(data);
        this.typeParameterClass = typeParameterClass;
        var listOfFields = Arrays.asList(typeParameterClass.getFields());
    }
}
