package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Flight;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeSearchStrategy implements ISearchStrategy {
    public Collection<ISearchStrategy> strategies = new
            ArrayList<ISearchStrategy>();

    @Override
    public boolean filter(Flight f) {
        for (ISearchStrategy ss : strategies) {
            if (!ss.filter(f)) return false;
        }
        return true;
    }

    public void addSearchStrategy(ISearchStrategy s){
        strategies.add(s);
    }
}