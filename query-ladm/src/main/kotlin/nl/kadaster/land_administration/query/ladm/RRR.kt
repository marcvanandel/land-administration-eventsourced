@file:Suppress("JpaAttributeMemberSignatureInspection")

package nl.kadaster.land_administration.query.ladm

import nl.kadaster.land_administration.core.commons.RightType
import org.springframework.context.annotation.Profile
import java.time.LocalDateTime
import javax.persistence.*

abstract class RightRestrictionOrResponsibility(
        open val description: String,
        open val rightId: String,
        open val party: String,
        open val share: EmbeddableFraction,
        open val shareCheck: Boolean,
        open val timeSpec: LocalDateTime
) {
    constructor() : this("", "", "", EmbeddableFraction(0, 0), false, LocalDateTime.now())
}

data class Responsibility(
        override val description: String,
        override val rightId: String,
        override val party: String,
        override val share: EmbeddableFraction,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime
) : RightRestrictionOrResponsibility(description, rightId, party, share, shareCheck, timeSpec)

data class Restriction(
        override val description: String,
        override val rightId: String,
        override val party: String,
        override val share: EmbeddableFraction,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime
) : RightRestrictionOrResponsibility(description, rightId, party, share, shareCheck, timeSpec)

@Profile("query-ladm")
@Entity(name = "Right")
@Table(name = "rights_table")
@Access(AccessType.FIELD)
data class Right(
        override val description: String,
        @Id override val rightId: String,
        override val party: String,
        override val share: EmbeddableFraction,
        override val shareCheck: Boolean,
        override val timeSpec: LocalDateTime,
        val type: RightType
) : RightRestrictionOrResponsibility(description, rightId, party, share, shareCheck, timeSpec) {
    constructor() : this("", "", "", EmbeddableFraction(0, 0), false, LocalDateTime.now(), RightType.OWNERSHIP)
    constructor(description: String, rId: String, party: String) : this(description, rId, party, EmbeddableFraction(0, 0), false, LocalDateTime.now(), RightType.OWNERSHIP)
}

data class BasicAdministrativeUnit(
        val name: String,
        val type: BasicAdministrativeUnitType,
        val uid: String)

enum class BasicAdministrativeUnitType {

}
