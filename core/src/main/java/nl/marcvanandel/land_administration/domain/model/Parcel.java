package nl.marcvanandel.land_administration.domain.model;

import nl.marcvanandel.land_administration.command.Alienation;
import nl.marcvanandel.land_administration.domain.datatype.ParcelId;
import nl.marcvanandel.land_administration.domain.event.RightTransferedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.commandhandling.model.inspection.AggregateModel;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcedAggregate;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.SnapshotTrigger;

import java.util.Set;

/**
 * A Parcel represents an area in a country, a piece of land. To this all kind of rights and taxes are bound.
 *
 * @author Marc van Andel
 * @since 0.1 on 27-2-2017
 */
@AggregateRoot
public class Parcel extends EventSourcedAggregate<Parcel> {

    @AggregateIdentifier
    private ParcelId identifier;
    private String location;
    private Set<Right> rights;

    protected Parcel(
        AggregateModel<Parcel> model, EventBus eventBus, SnapshotTrigger snapshotTrigger
    ) {
        super(model, eventBus, snapshotTrigger);
    }

    @CommandHandler
    public void transfer(Alienation alienation) {
        // handle command
        apply(new RightTransferedEvent());
    }

    @EventSourcingHandler
    protected void handle(RightTransferedEvent event) {
        // update state
    }

    @Override
    public String identifierAsString() {
        return identifier.toString();
    }

}
