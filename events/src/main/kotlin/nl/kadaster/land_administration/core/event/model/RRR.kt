package nl.kadaster.land_administration.core.event.model

import nl.kadaster.land_administration.core.commons.RightId
import nl.kadaster.land_administration.core.commons.RightType
import nl.kadaster.land_administration.core.commons.SubjectId
import java.time.LocalDateTime

abstract class RightRestrictionOrResponsibility(
        open val description: String,
        open val rightId: RightId,
        open val party: SubjectId,
        open val share: Share,
        open val shareCheck: Boolean,
        open val timeSpec: LocalDateTime
)

data class Right(
        override val description: String,
        override val rightId: RightId,
        override val party: SubjectId,
        override val share: Share,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime,
        val type: RightType
) : RightRestrictionOrResponsibility(description, rightId, party, share, shareCheck, timeSpec)

data class Share(val numerator: Int, val denominator: Int)
