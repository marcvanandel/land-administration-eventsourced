package nl.kadaster.land_administration.command.handlers

import nl.kadaster.land_administration.command.util.CommandException
import nl.kadaster.land_administration.command.util.SharesTotalNotValid
import nl.kadaster.land_administration.command.api.*
import nl.kadaster.land_administration.command.model.Fraction
import nl.kadaster.land_administration.command.model.Right
import nl.kadaster.land_administration.command.model.RightRestrictionOrResponsibility
import nl.kadaster.land_administration.command.util.IdentifierGenerator
import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.RightId
import nl.kadaster.land_administration.core.commons.RightType
import nl.kadaster.land_administration.core.event.ObjectCreatedEvent
import nl.kadaster.land_administration.core.event.RightCreated
import nl.kadaster.land_administration.core.event.RightTransferred
import nl.kadaster.land_administration.core.event.model.Share
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import java.time.LocalDateTime

@Profile("command")
@Aggregate
class Object {

    @Autowired
    private lateinit var identifierGenerator: IdentifierGenerator

    @AggregateIdentifier
    private lateinit var aggregateId: ObjectId

    private val rightsRestrictionsAndResponsibilities: MutableSet<RightRestrictionOrResponsibility> = mutableSetOf<RightRestrictionOrResponsibility>()

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
        val rightId = identifierGenerator.nextRightId()
        val firstShare = command.owners.first()
        AggregateLifecycle.apply(
                RightCreated(aggregateId,
                        nl.kadaster.land_administration.core.event.model.Right(description = "the ownership descr.",
                                rightId = rightId,
                                party = firstShare.subjectId,
                                share = Share(firstShare.numerator, firstShare.denominator),
                                shareCheck = false,
                                timeSpec = LocalDateTime.now(),
                                type = RightType.OWNERSHIP)))
    }

    private fun validateFractionTotalOf1(command: CreateOwnershipCommand) {
        val sum = command.owners
                .map { s -> Fraction(s.numerator, s.denominator) }
                .reduce { acc, fraction -> acc.plus(fraction) }
        if (sum.numerator / sum.denominator != 1)
            throw SharesTotalNotValid(sum)
    }

    private fun validateOwnershipIsNotSetYet() {
        if (rightsRestrictionsAndResponsibilities.isNotEmpty())
            throw CommandException(
                    "Object [$aggregateId] already has an ownership right ([${rightsRestrictionsAndResponsibilities.first().party}])")
    }

    @CommandHandler
    fun handle(command: TransferOwnerShipCommand): String {
        if (!rightsRestrictionsAndResponsibilities.map { r -> r.party }.contains(command.sellingShare.subjectId))
            throw CommandException("Could not transfer ownership 'cause this share [${command.sellingShare}] has no share in current ownership!")
        else {
            val sellingRightId = rightsRestrictionsAndResponsibilities
                    .filter { r -> r.party == command.sellingShare.subjectId }
                    .map { r -> r.rId }.first() as RightId
            val newRightId = identifierGenerator.nextRightId()
            val firstBuyer = command.buyingSubjects.first()
            AggregateLifecycle.apply(
                    RightTransferred(
                            command.objectId,
                            sellingRightId,
                            nl.kadaster.land_administration.core.event.model.Right(description = "the ownership descr.",
                                    rightId = newRightId,
                                    party = firstBuyer.subjectId,
                                    share = Share(firstBuyer.numerator, firstBuyer.denominator),
                                    shareCheck = false,
                                    timeSpec = LocalDateTime.now(),
                                    type = RightType.OWNERSHIP)
                    ))
        }
        return "Ownership transferred!"
    }

    @EventSourcingHandler
    fun on(event: ObjectCreatedEvent) {
        aggregateId = event.objectId
    }

    @EventSourcingHandler
    fun on(event: RightCreated) {
        rightsRestrictionsAndResponsibilities.add(
                Right(
                        description = "The ownership of this parcel",
                        rId = event.right.rightId,
                        party = event.right.party,
                        share = Fraction(event.right.share.numerator, event.right.share.denominator),
                        shareCheck = false,
                        timeSpec = LocalDateTime.now(),
                        type = RightType.OWNERSHIP
                ))
    }

    @EventSourcingHandler
    fun on(event: RightTransferred) {
        rightsRestrictionsAndResponsibilities.removeIf { r ->
            r.rId == event.sellingRight
        }
        rightsRestrictionsAndResponsibilities.add(
                Right(
                        description = "The ownership of this parcel",
                        rId = event.buyingRight.rightId,
                        party = event.buyingRight.party,
                        share = Fraction(event.buyingRight.share.numerator, event.buyingRight.share.denominator),
                        shareCheck = false,
                        timeSpec = LocalDateTime.now(),
                        type = RightType.OWNERSHIP
                ))
    }
}
