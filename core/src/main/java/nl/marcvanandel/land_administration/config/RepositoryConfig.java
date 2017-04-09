package nl.marcvanandel.land_administration.config;

import nl.marcvanandel.land_administration.domain.model.Parcel;
import nl.marcvanandel.land_administration.domain.repository.ParcelRepository;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.context.annotation.Bean;

/**
 * @author postmb
 * @since 9-4-2017
 */
public class RepositoryConfig {

    @Bean
    public ParcelRepository parcelRepository(
        AggregateFactory<Parcel> kadastraalObjectAggregateFactory,
        EventStore koersRegistratieEventStore
    ) {
        return new ParcelRepository(
            kadastraalObjectAggregateFactory,
            koersRegistratieEventStore);
    }
}
