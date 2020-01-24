package nl.kadaster.land_administration.domain.state.model

import nl.kadaster.land_administration.coreapi.Fraction
import nl.kadaster.land_administration.coreapi.ObjectIdentifier
import nl.kadaster.land_administration.coreapi.RightId
import nl.kadaster.land_administration.coreapi.RightType
import java.time.LocalDateTime

class Right(
        val rightId: RightId,
        val type: RightType,
        val share: Fraction,
        val subjects: Set<Subject>)

abstract class RightRestrictionOrResponsibility(
        open val description: String,
        open val rId: ObjectIdentifier,
        open val share: Fraction,
        open val shareCheck: Boolean,
        open val timeSpec: LocalDateTime
)

data class Responsibility(
        override val description: String,
        override val rId: ObjectIdentifier,
        override val share: Fraction,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime
) : RightRestrictionOrResponsibility(description, rId, share, shareCheck, timeSpec)

data class Restriction(
        override val description: String,
        override val rId: ObjectIdentifier,
        override val share: Fraction,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime
) : RightRestrictionOrResponsibility(description, rId, share, shareCheck, timeSpec)

//data class Right(
//        override val description: String,
//        override val rId: ObjectIdentifier,
//        override val share: Fraction,
//        override val shareCheck: Boolean,
//        override val timeSpec: LocalDateTime
//) : RightRestrictionOrResponsibility(description, rId, share, shareCheck, timeSpec)

data class BasicAdministrativeUnit(
        val name: String,
        val type: BasicAdministrativeUnitType,
        val uid: ObjectIdentifier)

enum class BasicAdministrativeUnitType {

}
