package nl.kadaster.land_administration.command

import nl.kadaster.land_administration.coreapi.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.util.*

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
            throw DomainException("Object [$aggregateId] already has an ownership right ([${ownership!!.rightId}])")
    }

    @CommandHandler
    fun handle(command: TransferOwnerShipCommand) {
        if (ownership == null || !ownership!!.shares.contains(command.sellingShare))
            throw DomainException("Could not transfer ownership 'cause this share [${command.sellingShare}] has no share in current ownership!")
        else {
            AggregateLifecycle.apply(
                    OwnershipTransferredEvent(
                            command.objectId,
                            ownership!!.rightId,
                            command.sellingShare,
                            command.buyingSubjects))
        }
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

object ObjectIdGenerator {
    private var lastId: Long = 0L

    fun next(): ObjectId {
        return ObjectId(lastId++)
    }

}

object RightIdGenerator {
    private var lastId: Long = 0L

    fun next(): RightId {
        return RightId(lastId++)
    }

}
