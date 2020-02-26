package nl.kadaster.land_administration.command.api

import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.SubjectId
import org.axonframework.commandhandling.RoutingKey
import org.axonframework.modelling.command.TargetAggregateIdentifier

abstract class ObjectCommand(
        @RoutingKey @TargetAggregateIdentifier open val objectId: ObjectId
)

abstract class SubjectCommand(
        @RoutingKey @TargetAggregateIdentifier open val subjectId: SubjectId
)

data class CreateObjectCommand(override val objectId: ObjectId) : ObjectCommand(objectId)

data class CreateSubjectCommand(override val subjectId: SubjectId) : SubjectCommand(subjectId)

data class CreateOwnershipCommand(
        override val objectId: ObjectId,
        val owners: Set<Share>
) : ObjectCommand(objectId)

data class TransferOwnerShipCommand(
        override val objectId: ObjectId,
        val sellingShare: Share,
        val buyingSubjects: Set<Share>
) : ObjectCommand(objectId)

data class Share(val subjectId: SubjectId, val numerator: Int, val denominator: Int)