package nl.kadaster.land_administration.core.event

import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.RightId
import nl.kadaster.land_administration.core.commons.SubjectId
import nl.kadaster.land_administration.core.event.model.Right

data class ObjectCreatedEvent(
        val objectId: ObjectId
)

data class SubjectCreatedEvent(
        val subject: SubjectId
)

data class RightCreated(
        val objectId: ObjectId,
        val right: Right
)

data class RightTransferred(
        val objectId: ObjectId,
        val sellingRight: RightId,
        val buyingRight: Right
)
