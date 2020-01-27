package nl.kadaster.land_administration.command

import nl.kadaster.land_administration.coreapi.CreateSubjectCommand
import nl.kadaster.land_administration.coreapi.SubjectCreatedEvent
import nl.kadaster.land_administration.coreapi.SubjectId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Subject {

    @AggregateIdentifier
    private lateinit var subjectId: SubjectId

    // Required by Axon
    constructor()

    @CommandHandler
    constructor(command: CreateSubjectCommand) {
        val aggragateId = command.subjectId
        AggregateLifecycle.apply(SubjectCreatedEvent(aggragateId))
    }

    @EventSourcingHandler
    fun on(event: SubjectCreatedEvent) {
        subjectId = event.subject
    }

}
