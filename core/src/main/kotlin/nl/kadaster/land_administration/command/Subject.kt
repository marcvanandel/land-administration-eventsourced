package nl.kadaster.land_administration.command

import nl.kadaster.land_administration.coreapi.CreateSubjectCommand
import nl.kadaster.land_administration.coreapi.SubjectCreatedEvent
import nl.kadaster.land_administration.coreapi.SubjectId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateLifecycle

class Subject {
    private lateinit var subjectId: SubjectId

    // Required by Axon
    constructor()

    @CommandHandler
    constructor(command: CreateSubjectCommand) {
        val aggragateId = SubjectIdGenerator.next()
        AggregateLifecycle.apply(SubjectCreatedEvent(aggragateId))
    }

    @EventSourcingHandler
    fun on(event: SubjectCreatedEvent) {
        subjectId = event.subject
    }

}

object SubjectIdGenerator {
    private var lastId: Long = -1L

    fun next(): SubjectId {
        return SubjectId(lastId++)
    }

}
