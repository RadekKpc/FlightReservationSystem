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

## User Interface

User interface currently includes main page, add carrier page and add user page -> they are supposed to be for superuser(admin) only, but currently logging in is not implemented in m1

### Main page
![mainPage](https://i.imgur.com/ooC5wJp.png)
On the left side we can see logo and buttons to access multiple other panels.

Clicking the button opens panel on the right side, left side stays intact.

### Add Uer

The add user panel allows us to add users to database, before sending request to database it checks wheter first and last names are empty and checks regex for email.
![addUserWrongEmail](https://i.imgur.com/as5FOBy.png)
For example we can see that the email input is wrong.

![addUserPositive](https://i.imgur.com/aSOAKIm.png)
If everything is okay the request to database is sent.

### Add Carrier
The add carrier panel allows us to add carriers to database.
![addCarrierPositive](https://i.imgur.com/Wg5DWmm.png)
As we can see above - the carrier was successfully added to database.

## Design patterns used
* **MVC** - it is not completed in m1 yet(we do not yet have property mapping and binding to data model), we want to seperate representation of information from the way it is presented and accepted from the user in view part(gui)
* **DAO** - 
* #TODO

## Task distribution
We distributed tasks among ourselves using JIRA so that we could keep track on our progress and try to distribute them more evenly 
### Frontend
* **start of frontend project** - Wiktor Kamiński
* **generified REST client** - Wiktor Kamiński
* **make GUI for main component** - Wiktor Kamiński
* **AddCarrier and AddUser GUIs** - Wiktor Kamiński
* **controller functionalities to change between scenes** - Karol Koptyra
* **HomeController** - Karol Koptyra
* **AddCarrierController** - Karol Koptyra
* **AddUserComponent** - Karol Koptyra
* **generified REST client** - Wiktor Kamiński

### Backend
* **start of backend project** - Paweł Miziołek
* **data model** - Paweł Miziołek
* **add Destination to data model** - Radosław Kopeć
* **obtain SQL database** - Radosław Kopeć
* **add Hibernate** - Radosław Kopeć
* **generating sample data** - Paweł Miziołek
* **create user endpoint** - Radosław Kopeć
* **create Spring repositories with JPA** - Radosław Kopeć
* **established data base connection** - Radosław Kopeć
* **adding services** - Paweł Miziołek
* **generify endpoint code** - Paweł Miziołek
* **implementation of SQLite** - Paweł Miziołek

### Documentation

* **documentation in readme** - Karol Koptyra 
* **fixes in documentation** - everyone in team 

