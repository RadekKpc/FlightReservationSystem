package com.wesolemarcheweczki.backend.generator;

import com.wesolemarcheweczki.backend.model.*;
import com.wesolemarcheweczki.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class GenerateDataOnInit implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(GenerateDataOnInit.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CarrierRepository carrierRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        Generator generator = new Generator();
//        generator.generate();
//        generator.clients.forEach( c -> clientRepository.save(c));
//        generator.carriers.forEach( c -> carrierRepository.save(c));
//        generator.flights.forEach( c -> entityManager.save(c));
//        generator.orders.forEach( c -> orderRepository.save(c));
//        generator.tickets.forEach( c -> entityManager.save(c));
        Carrier carrier = new Carrier("Radek");
        Client client = new Client("Pawel", "Kopec");
        Flight flight = new Flight("CODEXX", carrier, generateRandomDate(),
                generateRandomDate(), 20);
        Passenger passenger = new Passenger("Wiktor", "Kaminski");

        Order order = new Order(client);
        Ticket ticket = new Ticket(passenger, flight, order, 2, 200);


        carrierRepository.save(carrier);
        clientRepository.save(client);
        flightRepository.save(flight);
        orderRepository.save(order);
        ticketRepository.save(ticket);
        logger.info("Zainicjalizowalem");
    }
    private LocalDateTime generateRandomDate() {
        long min = LocalDateTime.of(2020, 1, 1, 0, 0).toEpochSecond(ZoneOffset.ofHours(0));
        long max = LocalDateTime.of(2020, 1, 8, 23, 59).toEpochSecond(ZoneOffset.ofHours(0));
        long randomDay = ThreadLocalRandom.current().nextLong(min, max);
        return LocalDateTime.ofEpochSecond(randomDay, 0, ZoneOffset.ofHours(0));
    }
}
