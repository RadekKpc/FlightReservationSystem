package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Flight;

public interface SearchStrategy {
    boolean filter(Flight f);
}