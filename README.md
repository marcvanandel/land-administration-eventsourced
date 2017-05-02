# Land Administration in an EventSourced way

This repo is an example on how a Land Administration system could be set up in a Event Sourced way. Event Sourcing is an architecture promoted in the Domain Driven Design methodology. It follows on the segregation of the services called the CQRS architectural pattern, Command Query Responsibility Segregation. This repo shows how these patterns can be applied to a Land Administration system as a showcase for a (Dutch) Core Administration (Basisregistratie). This is a very (!) simplified system of how [Kadaster](www.kadaster.nl) is using [AxonFramework](http://www.axonframework.org) to apply CQRS and Event Sourcing to their internal Land Administration system.

For the domain please have a look at the ISO standard of [Land Administration Domain Model](http://www.gdmc.nl/publications/2011/Land_Administration_Domain_Model.pdf) (LADM)

### module: view-ownership

A view for getting the current owner of a parcel. This can be implemented lazy load, i.e.,  when requesting a given parcel all events for that parcel are replayed.

### module: view-ownership-history

A view for obtaining the ownership with all its history. This can not be implemented lazy load, i.e., when requesting the ownsership of a given subject, all associated parcels are unknown.

# Kadaster Open Source & Event Sourced

- Engels
- Event Sourced
- LADM van Chrit Lemmen
- DSLs?
- Technology stack:
  - JVM?
    - Java
    - Scala
    - Clojure
  - Kafka?
- Hoe krijgen we dit als business pitch voor elkaar?
  - Kadaster International: Kees de Zeeuw
  - Frank Thierolff?
  - MBP: partner (niet alleen geo!), platform (niet alleen geo!)
  