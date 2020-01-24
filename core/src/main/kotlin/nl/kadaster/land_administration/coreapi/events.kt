package nl.kadaster.land_administration.coreapi

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
