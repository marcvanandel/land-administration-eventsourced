package nl.marcvanandel.land_administration.domain.repository;

import nl.marcvanandel.land_administration.domain.datatype.Identity;
import nl.marcvanandel.land_administration.domain.model.Parcel;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStore;

import java.util.concurrent.Callable;

public class ParcelRepository implements Repository<Parcel> {

    private final EventSourcingRepository<Parcel> delegate;
    
    public ParcelRepository(
        AggregateFactory<Parcel> aggregateFactory, EventStore eventStore
    ) {
        this.delegate = new EventSourcingRepository<>(aggregateFactory, eventStore);
    }


    @Override
    public Aggregate<Parcel> load(String aggregateIdentifier) {
        return this.load(aggregateIdentifier, null);
    }

    @Override
    public Aggregate<Parcel> load(
        String aggregateIdentifier, Long expectedVersion
    ) {
        try {
            return delegate.load(aggregateIdentifier, expectedVersion);
        } catch (AggregateNotFoundException e) {
            return null;
        }
    }

    @Override
    public Aggregate<Parcel> newInstance(Callable<Parcel> factoryMethod) throws Exception {
        return delegate.newInstance(factoryMethod);
    }

    public Aggregate<Parcel> load(Identity aggregateIdentifier) {
        if (aggregateIdentifier == null) {
            throw new IllegalArgumentException("Argument 'aggregateIdentifier' mag niet null zijn!");
        }
        return load(aggregateIdentifier.getId().toString());
    }

    public Aggregate<Parcel> add(Parcel aggregateRoot) {
        try {
            return this.newInstance(() -> aggregateRoot);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
