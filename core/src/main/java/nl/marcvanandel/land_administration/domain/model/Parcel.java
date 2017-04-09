package nl.marcvanandel.land_administration.domain.model;

import nl.marcvanandel.land_administration.domain.datatype.ParcelId;
import nl.marcvanandel.land_administration.domain.datatype.RightId;
import nl.marcvanandel.land_administration.domain.datatype.SubjectId;
import nl.marcvanandel.land_administration.domain.event.RightTransferedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateMember;
import org.axonframework.commandhandling.model.AggregateRoot;
import org.axonframework.commandhandling.model.inspection.AggregateModel;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.SnapshotTrigger;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.util.Optional;
import java.util.Set;

/**
 * A Parcel represents an area in a country, a piece of land. To this all kind of rights and taxes are bound.
 *
 * @author Marc van Andel
 * @since 0.1 on 27-2-2017
 */
@AggregateRoot
public class Parcel {

    @AggregateIdentifier
    private ParcelId identifier;
    private String location;
    
    @AggregateMember(type = Right.class)
    private Set<Right> rights;
    
    @CommandHandler
    public void transfer(RightId rightId, SubjectId sellingSubjectId, Set<Subject> buyingSubjects) {
        apply(new RightTransferedEvent(identifier, rightId, sellingSubjectId).setBuyingSubjects(buyingSubjects));
    }

    @EventSourcingHandler
    protected void handle(RightTransferedEvent event) {
        // update state
        Optional<Right> right = rights.stream().filter(r -> r.getRightId().equals(event.getRightId())).findFirst();
        right.ifPresent(s -> {
            s.getSubjects().removeIf(subject -> subject.getSubjectId().equals(event.getSellingSubject()));
            s.getSubjects().addAll(event.getBuyingSubjects());
        });
    }

    public String identifierAsString() {
        return identifier.toString();
    }

    public String getLocation() {
        return location;
    }

    public Set<Right> getRights() {
        return rights;
    }
}
