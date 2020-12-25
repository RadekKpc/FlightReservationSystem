package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Flight;

public interface ISearchStrategy {
    boolean filter(Flight f);
}