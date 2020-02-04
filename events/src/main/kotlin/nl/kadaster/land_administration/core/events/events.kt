package nl.kadaster.land_administration.core.events

import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.RightId
import nl.kadaster.land_administration.core.commons.Share
import nl.kadaster.land_administration.core.commons.SubjectId

data class ObjectCreatedEvent(
        val objectId: ObjectId
)

data class SubjectCreatedEvent(
        val subject: SubjectId
)

data class OwnershipCreatedEvent(
        val objectId: ObjectId,
        val rightId: RightId,
        val shares: Set<Share>
)

data class OwnershipTransferredEvent(
        val objectId: ObjectId,
        val rightId: RightId,
        val sellingShare: Share,
        val buyingSubjects: Set<Share>
)
