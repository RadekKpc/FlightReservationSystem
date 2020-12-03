# Ticket system


## Contributors
- [Paweł Miziołek](https://github.com/pawel00100)

- [Wiktor Kamiński](https://github.com/WikKam)

- [Karol Koptyra](https://github.com/what-ewer)

- [Radosław Kopeć](https://github.com/radekkpc)

## Technologies

- Java 14
- Spring Boot
- Spring JPA
- JavaFX

## Data model
![uml diagram](https://i.imgur.com/SFpS1G6.png)
 
 **Model includes classes:**
- Flight - *information about each individual flight - contains arrival and departure times, source and destination locations, flight carrier and base flight price*
- Client - *information about each individual client - their first and last name and email*
- Order - *information about order - to which client it belongs - it is also connected to tickets so that you can see which tickets belong to the order*
- Ticket - *information about the ticket - to which passenger it belongs, from which order as well as some basic information such as final (possibly discounted) cost and seat*
- Passenger - *information about passenger - their first and last name(as client can buy tickets for other family members)*
- Location - *information about location airport - city, country and airport id*
- Carrier - *information about carrier - carriers name*

## Architecture

Software is split into separate applications. Backend based on Spring that publishes REST endpoints, and frontend with UI that consumes API.

## Run
 - Open Command line in /backend and type gradle bootRun, then in /frontend type gradle run
