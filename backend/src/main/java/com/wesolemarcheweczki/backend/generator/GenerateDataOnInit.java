package com.wesolemarcheweczki.backend.generator;

import com.wesolemarcheweczki.backend.model.*;
import com.wesolemarcheweczki.backend.repository.*;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
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
    private PassengerRepository passengerRepository;
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
        Client client = new Client("Pawel","Kopec");
        Flight flight = new Flight("CODEXX",carrier,generateRandomDate(),
                generateRandomDate(),20);
        Passenger passenger = new Passenger("Wiktor","Kaminski");
        Ticket ticket = new Ticket(passenger,flight,2,200);

        ArrayList<Ticket> tickets = new ArrayList<>();
        tickets.add(ticket);
//        Order order = new Order(client,tickets);
        
        carrierRepository.save(carrier);
        clientRepository.save(client);
        flightRepository.save(flight);
        passengerRepository.save(passenger);
        ticketRepository.save(ticket);
//        orderRepository.save(order);
        logger.info("Zainicjalizowalem");
    }
    private LocalDateTime generateRandomDate() {
        long min = LocalDateTime.of(2020, 1, 1, 0, 0).toEpochSecond(ZoneOffset.ofHours(0));
        long max = LocalDateTime.of(2020, 1, 8, 23, 59).toEpochSecond(ZoneOffset.ofHours(0));
        long randomDay = ThreadLocalRandom.current().nextLong(min, max);
        return LocalDateTime.ofEpochSecond(randomDay, 0, ZoneOffset.ofHours(0));
    }
}
