package nl.kadaster.land_administration.command.model

import nl.kadaster.land_administration.core.commons.ObjectIdentifier
import nl.kadaster.land_administration.core.commons.RightType
import nl.kadaster.land_administration.core.commons.SubjectId
import java.time.LocalDateTime

//class Right(
//        val rightId: RightId,
//        val type: RightType,
//        val share: Fraction,
//        val subjects: Set<Subject>)

abstract class RightRestrictionOrResponsibility(
        open val description: String,
        open val rId: ObjectIdentifier,
        open val party: SubjectId,
        open val share: Fraction,
        open val shareCheck: Boolean,
        open val timeSpec: LocalDateTime
)

data class Responsibility(
        override val description: String,
        override val rId: ObjectIdentifier,
        override val party: SubjectId,
        override val share: Fraction,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime
) : RightRestrictionOrResponsibility(description, rId, party, share, shareCheck, timeSpec)

data class Restriction(
        override val description: String,
        override val rId: ObjectIdentifier,
        override val party: SubjectId,
        override val share: Fraction,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime
) : RightRestrictionOrResponsibility(description, rId, party, share, shareCheck, timeSpec)

data class Right(
        override val description: String,
        override val rId: ObjectIdentifier,
        override val party: SubjectId,
        override val share: Fraction,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime,
        val type: RightType
) : RightRestrictionOrResponsibility(description, rId, party, share, shareCheck, timeSpec)

data class BasicAdministrativeUnit(
        val name: String,
        val type: BasicAdministrativeUnitType,
        val uid: ObjectIdentifier)

enum class BasicAdministrativeUnitType {

}
