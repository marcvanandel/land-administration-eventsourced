package nl.kadaster.land_administration.command.handlers

import nl.kadaster.land_administration.command.api.CommandException
import nl.kadaster.land_administration.command.api.SharesTotalNotValid
import nl.kadaster.land_administration.command.api.CreateObjectCommand
import nl.kadaster.land_administration.command.api.CreateOwnershipCommand
import nl.kadaster.land_administration.command.api.TransferOwnerShipCommand
import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.RightId
import nl.kadaster.land_administration.core.commons.Share
import nl.kadaster.land_administration.core.events.ObjectCreatedEvent
import nl.kadaster.land_administration.core.events.OwnershipCreatedEvent
import nl.kadaster.land_administration.core.events.OwnershipTransferredEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Object {

    @AggregateIdentifier
    private lateinit var aggregateId: ObjectId
    var ownership: Ownership? = null

    // Required by Axon
    constructor()

    @CommandHandler
    constructor(command: CreateObjectCommand) {
        val aggragateId = command.objectId
        AggregateLifecycle.apply(ObjectCreatedEvent(aggragateId))
    }

    @CommandHandler
    fun handle(command: CreateOwnershipCommand) {
        validateOwnershipIsNotSetYet()
        validateFractionTotalOf1(command)
        AggregateLifecycle.apply(OwnershipCreatedEvent(aggregateId, RightId(aggregateId.localId), command.owners))
    }

    private fun validateFractionTotalOf1(command: CreateOwnershipCommand) {
        val sum = command.owners
                .map { s -> s.fraction }
                .reduce { acc, fraction -> acc.plus(fraction) }
        if (sum.numerator / sum.denominator != 1)
            throw SharesTotalNotValid(sum)
    }

    private fun validateOwnershipIsNotSetYet() {
        if (ownership != null)
            throw CommandException("Object [$aggregateId] already has an ownership right ([${ownership!!.rightId}])")
    }

    @CommandHandler
    fun handle(command: TransferOwnerShipCommand): String {
        if (ownership == null || !ownership!!.shares.contains(command.sellingShare))
            throw CommandException("Could not transfer ownership 'cause this share [${command.sellingShare}] has no share in current ownership!")
        else {
            AggregateLifecycle.apply(
                    OwnershipTransferredEvent(
                            command.objectId,
                            ownership!!.rightId,
                            command.sellingShare,
                            command.buyingSubjects))
        }
        return "Ownership transferred!"
    }

    @EventSourcingHandler
    fun on(event: ObjectCreatedEvent) {
        aggregateId = event.objectId
    }

    @EventSourcingHandler
    fun on(event: OwnershipCreatedEvent) {
        ownership = Ownership(event.rightId)
        ownership!!.shares.addAll(event.shares)
    }

    @EventSourcingHandler
    fun on(event: OwnershipTransferredEvent) {
        ownership!!.shares.remove(event.sellingShare)
        ownership!!.shares.addAll(event.buyingSubjects)
    }

}

data class Ownership(val rightId: RightId, val shares: MutableSet<Share> = mutableSetOf())
