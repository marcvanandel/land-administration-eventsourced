package nl.marcvanandel.land_administration.command;

import nl.marcvanandel.land_administration.domain.model.Parcel;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventBus;

/**
 * // TODO
 *
 * @author Marc van Andel
 * @since 0.1 on 27-2-2017
 */
public class ParcelCommandHandler {

    private Repository<Parcel> repository;
    private EventBus eventBus;
    
    public ParcelCommandHandler(Repository<Parcel> repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }
    
    @CommandHandler
    public void createNewParcel() {
//        Parcel.;
//        repository.newInstance(() -> new Parcel(command.getBankAccountId(), command.getOverdraftLimit()));
    }
    
    @CommandHandler
    public void allianate() {
        //
    }
}
