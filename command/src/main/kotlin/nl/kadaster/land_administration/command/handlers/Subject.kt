package nl.kadaster.land_administration.command.handlers

import nl.kadaster.land_administration.command.api.CreateSubjectCommand
import nl.kadaster.land_administration.core.commons.SubjectId
import nl.kadaster.land_administration.core.event.SubjectCreatedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.context.annotation.Profile

@Profile("command")
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
