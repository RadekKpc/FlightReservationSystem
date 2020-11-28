package com.wesolemarcheweczki.backend.generator;

import com.wesolemarcheweczki.backend.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@PropertySource("classpath:backend.properties")
public class Generator {
    static final String CARRIER = "carrier";
    static final String TICKET = "ticket";
    static final String ORDER = "order";
    static final String FLIGHT = "flight";
    static final String CLIENT = "client";
    private static final Random random = new Random();

    private final Map<String, Integer> counters = new HashMap<>();

    List<Carrier> carriers = new ArrayList<>();
    List<Flight> flights = new ArrayList<>();
    List<Ticket> tickets = new ArrayList<>();
    List<Order> orders = new ArrayList<>();
    List<Client> clients = new ArrayList<>();

    @Value("#{PropertySplitter.map('${com.wesolemarcheweczki.backend.generator}')}")
    private Map<String, Integer> instanceNumbers;


    public void generate() {
        generateCarriers();
        generateClients();
        generateFlights(); //needs carriers
        generateOrders(); //needs flights and clients

    }

    private void generateClients() {
        var count = instanceNumbers.get(CARRIER);
        for (int i = 0; i < count; i++) {
            generateClient();
        }
    }

    private void generateClient() {
        var name1 = generateName(CLIENT);
        var client = new Client(name1, "lastname");
        clients.add(client);
    }

    private List<Ticket> generateTickets() {
        var singleOrderTickets = new ArrayList<Ticket>();
        var count = instanceNumbers.get(TICKET);
        for (int i = 0; i < count; i++) {
            generateTicket(singleOrderTickets);
        }
        return singleOrderTickets;
    }

    private void generateTicket(ArrayList<Ticket> singleOrderTickets) {
        var passenger = new Passenger(generateName("passenger"), "lastname");
        var ticket = new Ticket(passenger, getRandomElement(flights), generateInt("seat"), 1000);
        singleOrderTickets.add(ticket);
        tickets.add(ticket);
    }

    private void generateOrders() {
        var count = instanceNumbers.get(ORDER);
        for (int i = 0; i < count; i++) {
            generateOrder();
        }
    }

    private void generateOrder() {
        var order = new Order(getRandomElement(clients), generateTickets());
        orders.add(order);
    }

    private void generateFlights() {
        var count = instanceNumbers.get(FLIGHT);
        for (int i = 0; i < count; i++) {
            generateFlight();
        }
    }

    private void generateFlight() {
        var name = generateName(FLIGHT);
        var start = generateRandomDate();
        var end = generateRandomDate().plusHours(8);
        var flight = new Flight(name, getRandomElement(carriers), start, end, 200);
        flights.add(flight);
    }

    private void generateCarriers() {
        var count = instanceNumbers.get(CARRIER);
        for (int i = 0; i < count; i++) {
            generateCarrier();
        }
    }

    private void generateCarrier() {
        var name = generateName(CARRIER);
        var carrier = new Carrier(name);
        carriers.add(carrier);
    }

    private String generateName(String name) {
        return name + generateInt(name);
    }

    private int generateInt(String name) {
        ensureMapContainsKey(name);
        var counter = counters.get(name);
        counter++;
        counters.put(name, counter);
        return counter;
    }

    private void ensureMapContainsKey(String name) {
        if (!counters.containsKey(name)) {
            counters.put(name, 0);
        }
    }

    private LocalDateTime generateRandomDate() {
        long min = LocalDateTime.of(2020, 1, 1, 0, 0).toEpochSecond(ZoneOffset.ofHours(0));
        long max = LocalDateTime.of(2020, 1, 8, 23, 59).toEpochSecond(ZoneOffset.ofHours(0));
        long randomDay = ThreadLocalRandom.current().nextLong(min, max);
        return LocalDateTime.ofEpochSecond(randomDay, 0, ZoneOffset.ofHours(0));
    }

    private <T> T getRandomElement(List<T> list) {
        int size = list.size();
        int n = random.nextInt(size);
        return list.get(n);
    }
}
