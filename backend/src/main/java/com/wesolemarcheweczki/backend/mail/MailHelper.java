package com.wesolemarcheweczki.backend.mail;

import com.wesolemarcheweczki.backend.model.Flight;
import com.wesolemarcheweczki.backend.model.Ticket;

import java.util.List;

public class MailHelper {

    public static String generateFLightString(Flight flight, List<Ticket> tickets) {
        String passengers = tickets.stream().map(t -> t.getPassenger().display()).reduce("", (a, b) -> a + '\n' + b);
        return String.format("%s\n\nWith following passengers: %s", flight.display(), passengers);
    }
}
