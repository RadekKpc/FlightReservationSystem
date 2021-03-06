package com.wesolemarcheweczki.backend.generator;

import com.wesolemarcheweczki.backend.dao.*;
import com.wesolemarcheweczki.backend.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@PropertySource("classpath:backend.properties")
@Component
public class Generator implements ApplicationRunner {
    static final String CARRIER = "carrier";
    static final String TICKET = "ticket";
    static final String ORDER = "order";
    static final String FLIGHT = "flight";
    static final String CLIENT = "client";
    static final String CLIENT_EMAIL = "client_email";
    static final String CLIENT_PWD = "client_pwd";
    static final String LOCATION = "location";

    private static final Random random = new Random();
    private static final Logger logger = LoggerFactory.getLogger(Generator.class);
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final Map<String, Integer> counters = new HashMap<>();

    List<Carrier> carriers = new ArrayList<>();
    List<Flight> flights = new ArrayList<>();
    List<Ticket> tickets = new ArrayList<>();
    List<Order> orders = new ArrayList<>();
    List<Client> clients = new ArrayList<>();
    List<Location> locations = new ArrayList<>();

    @Autowired
    private CarrierDAO carrierDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private FlightDAO flightDAO;
    @Autowired
    private TicketDAO ticketDAO;
    @Autowired
    private LocationDAO locationDAO;

    @Value("#{PropertySplitter.map('${com.wesolemarcheweczki.backend.generator}')}")
    private Map<String, Integer> instanceNumbers;


    @Override
    public void run(ApplicationArguments args) {
        logger.info("Generating sample");
        generateObjects();
        saveObjects();
        logger.info("Inserted sample");

    }

    private void generateObjects() {
        generateLocations();
        generateCarriers();
        generateClients();
        generateFlights(); //needs carriers
        generateOrders(); //needs flights and clients
    }

    private void generateClientWithFlightCollision() {
        LocalDateTime departure_main = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime arrival_main = LocalDateTime.of(2020, 1, 1, 6, 0);

        LocalDateTime collision_departure_1 = LocalDateTime.of(2019, 12, 31, 0, 0);
        LocalDateTime collision_arrival_1 = LocalDateTime.of(2020, 1, 1, 7, 0);
        LocalDateTime collision_departure_2 = LocalDateTime.of(2020, 1, 1, 5, 0);
        LocalDateTime collision_arrival_2 = LocalDateTime.of(2020, 1, 1, 10, 0);

        Client client = clientDAO.getByEmail("test@test.com");
        Flight main = new Flight("MainFlight",getRandomElement(carriers),departure_main,arrival_main,100,999,getRandomElement(locations),getRandomElement(locations));
        Flight collision_1 = new Flight("collision1",getRandomElement(carriers),collision_departure_1,collision_arrival_1,100,999,getRandomElement(locations),getRandomElement(locations));
        Flight collision_2 = new Flight("collision2",getRandomElement(carriers),collision_departure_2,collision_arrival_2,100,999,getRandomElement(locations),getRandomElement(locations));

        flightDAO.add(main);
        flightDAO.add(collision_1);
        flightDAO.add(collision_2);

        Order order = new Order(client);
        orderDAO.add(order);

        Ticket ticket = new Ticket(new Passenger("Test", "Collision"), main, order, 33, 123, false);
        ticketDAO.add(ticket);
    }

    private void saveObjects() {
        locationDAO.addAll(locations);
        carrierDAO.addAll(carriers);
        clientDAO.addAll(clients);
        flightDAO.addAll(flights);
        orderDAO.addAll(orders);
        ticketDAO.addAll(tickets);
        clientDAO.addAll(generateDefaultClients());
        generateClientWithFlightCollision();
    }

    private void generateClients() {
        var count = instanceNumbers.get(CARRIER);
        for (int i = 0; i < count; i++) {
            generateClient();
        }
    }

    private void generateClient() {
        var name1 = generateName(CLIENT);
        var email = generateName(CLIENT_EMAIL);
        var pwd = name1 + "pwd";

        var client = new Client(name1, "lastname", email + "@sample.com", encoder.encode(pwd));
        clients.add(client);
    }

    private List<Ticket> generateTickets(Order order) {
        var singleOrderTickets = new ArrayList<Ticket>();
        var count = instanceNumbers.get(TICKET);
        for (int i = 0; i < count; i++) {
            generateTicket(singleOrderTickets, order);
        }
        return singleOrderTickets;
    }

    private void generateTicket(ArrayList<Ticket> singleOrderTickets, Order order) {
        var passenger = new Passenger(generateName("passenger"), "lastname");
        var ticket = new Ticket(passenger, getRandomElement(flights), order, generateInt("seat"), 800, false);
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
        var order = new Order(getRandomElement(clients));
        orders.add(order);
        generateTickets(order);
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
        var end = start.plusHours(8);
        var flight = new Flight(name, getRandomElement(carriers), start, end, random.nextInt(200)+50, random.nextInt(1000)+200, getRandomElement(locations), getRandomElement(locations));
        flights.add(flight);
    }

    private void generateCarriers() {
        var count = instanceNumbers.get(CARRIER);
        for (int i = 0; i < count; i++) {
            generateCarrier();
        }
    }

    private void generateLocations() {
        var count = instanceNumbers.get(LOCATION);
        for (int i = 0; i < count; i++) {
            generateLocation();
        }
    }

    private void generateLocation() {
        var airportId = generateName(LOCATION);
        var city = generateName("city");
        var country = generateName("country");
        locations.add(new Location(airportId, city, country));
    }

    private void generateCarrier() {
        var name = generateName(CARRIER);
        var carrier = new Carrier(name);
        carriers.add(carrier);
    }

    private List<Client> generateDefaultClients() {
        var list = new ArrayList<Client>();
        list.add(new Client("test", "test", "test@test.com", encoder.encode("test")));
        list.add(new Client("aa", "bb", "aa@aa.aa", encoder.encode("aa")));
        list.add(new Client("admin", "admin", "admin@admin.com", encoder.encode("admin"),"ROLE_ADMIN"));
        return list;
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
        long min = LocalDateTime.of(2021, 1, 1, 0, 0).toEpochSecond(ZoneOffset.ofHours(0));
        long max = LocalDateTime.of(2022, 1, 1, 0, 0).toEpochSecond(ZoneOffset.ofHours(0));
        long randomDay = ThreadLocalRandom.current().nextLong(min, max);
        return LocalDateTime.ofEpochSecond(randomDay, 0, ZoneOffset.ofHours(0));
    }

    private <T> T getRandomElement(List<T> list) {
        int size = list.size();
        int n = random.nextInt(size);
        return list.get(n);
    }
}
