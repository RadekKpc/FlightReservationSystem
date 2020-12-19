package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Flight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateSearchStrategy implements ISearchStrategy{

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private boolean departure;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T''HH:mm:ss");

    public DateSearchStrategy(String fromDate, String toDate, boolean departure) {
        this.toDate = LocalDateTime.parse(toDate, formatter);
        this.fromDate = LocalDateTime.parse(fromDate, formatter);
        this.departure = departure;
    }

    @Override
    public boolean filter(Flight f) {

        return departure
                ? (this.fromDate.isBefore(f.getDeparture()) && this.toDate.isAfter(f.getDeparture()))
                : (this.fromDate.isBefore(f.getArrival()) && this.toDate.isAfter(f.getArrival()));
    }
}
