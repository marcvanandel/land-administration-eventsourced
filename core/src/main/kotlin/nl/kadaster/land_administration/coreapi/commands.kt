package nl.kadaster.land_administration.coreapi

import org.axonframework.commandhandling.RoutingKey
import org.axonframework.modelling.command.TargetAggregateIdentifier

class CreateObjectCommand(
        @RoutingKey @TargetAggregateIdentifier val objectId: ObjectId
)

class CreateSubjectCommand(
        @RoutingKey @TargetAggregateIdentifier val subjectId: SubjectId
)

class CreateOwnershipCommand(
        @RoutingKey @TargetAggregateIdentifier val objectId: ObjectId,
        val owners: Set<Share>
)

data class TransferOwnerShipCommand(
        @RoutingKey @TargetAggregateIdentifier val objectId: ObjectId,
        val sellingShare: Share,
        val buyingSubjects: Set<Share>
)
