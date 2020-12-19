package com.wesolemarcheweczki.frontend.search;

import com.wesolemarcheweczki.frontend.model.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateSearchStrategy implements ISearchStrategy{

    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private boolean departure;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DateSearchStrategy(LocalDate fromDate, LocalDate toDate, boolean departure) {
        LocalDate date = null;
        this.toDate = LocalDate.parse(toDate.toString(), formatter).atTime(23,59);
        this.fromDate =  LocalDate.parse(fromDate.toString(), formatter).atStartOfDay();
        this.departure = departure;
    }

    @Override
    public boolean filter(Flight f) {

        return departure
                ? (this.fromDate.isBefore(f.getDeparture()) && this.toDate.isAfter(f.getDeparture()))
                : (this.fromDate.isBefore(f.getArrival()) && this.toDate.isAfter(f.getArrival()));
    }
}
