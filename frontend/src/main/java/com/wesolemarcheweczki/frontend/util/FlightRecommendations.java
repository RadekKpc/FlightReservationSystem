package com.wesolemarcheweczki.frontend.util;

import com.wesolemarcheweczki.frontend.model.*;
import com.wesolemarcheweczki.frontend.search.*;

import java.time.LocalDate;
import java.util.*;

public class FlightRecommendations {

    private static final Random rand = new Random();

    /**
     * flight recommendations are based on:
     * - places client has already booked tickets from
     * - places client has already booked tickets to
     * - carrier that client used most often
     * - flights that are soon (up to 5 in next 2 weeks)
     * - some of the cheapest flights
     *
     * # there are at max 20 recommended flights
     * **/
    public static List<Flight> recommendedFlights(List<Flight> flightList, List<Ticket> tickets) {
        List<Flight> flightRecommended = new LinkedList<>();

        CompositeSearchStrategy searchStrategy = new CompositeSearchStrategy();
        searchStrategy.addSearchStrategy(new DateSearchStrategy(LocalDate.now(), LocalDate.now().plusDays(31),true));
        searchStrategy.addSearchStrategy(new CarrierSearchStrategy(getMostPopularCarrier(tickets)));

        Location source = getMostPopularLocation(tickets, true);
        if (source != null)
            searchStrategy.addSearchStrategy(new PlaceSearchStrategy(source.getCity(), source.getCountry(),true));

        Location destination = getMostPopularLocation(tickets, false);
        if (destination != null)
            searchStrategy.addSearchStrategy(new PlaceSearchStrategy(destination.getCity(), destination.getCountry(), false));

        searchStrategy.addSearchStrategy(new PriceSearchStrategy(350));

        for (Flight f : flightList){
            if (searchStrategy.filterOr(f)){
                flightRecommended.add(f);
            }
        }
        Collections.shuffle(flightRecommended);

        return flightRecommended.subList(0, Math.min(20,flightRecommended.size()));
    }

    public static Carrier getMostPopularCarrier(List<Ticket> tickets){
        Map<Carrier, Integer> carriers = new HashMap<>();

        for (Ticket t : tickets) {
            Carrier c = t.getFlight().getCarrier();
            Integer val = carriers.get(c);
            carriers.put(c, val == null ? 1 : val + 1);
        }

        Map.Entry<Carrier, Integer> max = null;
        for (Map.Entry<Carrier, Integer> e : carriers.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }
        return max.getKey();
    }

    public static Location getMostPopularLocation(List<Ticket> tickets, boolean from) {
        Map<Location, Integer> carriers = new HashMap<>();

        for (Ticket t : tickets) {
            Location l = from ? t.getFlight().getSource() : t.getFlight().getDestination();
            Integer val = carriers.get(l);
            carriers.put(l, val == null ? 1 : val + 1);
        }

        Map.Entry<Location, Integer> max = null;
        for (Map.Entry<Location, Integer> e : carriers.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }
        return max.getKey();
    }

}
