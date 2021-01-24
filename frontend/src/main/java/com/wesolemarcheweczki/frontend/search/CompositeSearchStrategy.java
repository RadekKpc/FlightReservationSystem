package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Flight;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeSearchStrategy implements SearchStrategy {
    public Collection<SearchStrategy> strategies = new ArrayList<>();

    @Override
    public boolean filter(Flight f) {
        return strategies.stream().allMatch(s -> s.filter(f));
    }

    public boolean filterOr(Flight f) {
        return strategies.stream().anyMatch(s -> s.filter(f));
    }

    public void addSearchStrategy(SearchStrategy s) {
        strategies.add(s);
    }
}