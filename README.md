# Land Administration in a EventSourced way

This repo is an example on how a Land Administration system could be set up in a Event Sourced way. Event Sourcing is an architecture promoted in the Domain Driven Design methodology. It follows on the segregation of the services called the CQRS architectural pattern, Command Query Responsibility Segregation. This repo shows how these patterns can be applied to a Land Administration system as a showcase for a (Dutch) Core Administration (Basisregistratie). This is a very (!) simplified system of how [Kadaster](www.kadaster.nl) is using [AxonFramework](http://www.axonframework.org) to apply CQRS and Event Sourcing to their internal Land Administration system.

For the domain please have a look at the ISO standard of [Land Administration Domain Model](http://www.gdmc.nl/publications/2011/Land_Administration_Domain_Model.pdf) (LADM)
