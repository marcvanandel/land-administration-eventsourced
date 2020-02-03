package nl.kadaster.land_administration.query.ladm

import nl.kadaster.land_administration.core.commons.Fraction
import nl.kadaster.land_administration.core.commons.RightId
import nl.kadaster.land_administration.core.commons.Share
import nl.kadaster.land_administration.core.events.ObjectCreatedEvent
import nl.kadaster.land_administration.core.events.OwnershipCreatedEvent
import nl.kadaster.land_administration.core.events.OwnershipTransferredEvent
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.*

@Entity
data class ObjectRightsView(
        @Id val objectId: String,
        var ownershipId: String,
        @ElementCollection(fetch = FetchType.EAGER) val ownershipShares: MutableMap<String, EmbeddableFraction>) {

    constructor() : this("undefined")
    constructor(objectId: String) : this(objectId, "undefined", Collections.emptyMap())

    fun updateOwnershipId(rightId: RightId) {
        ownershipId = rightId.asString()
    }

    fun updateOwnershipShares(shares: Set<Share>) {
        ownershipShares.clear()
        ownershipShares.putAll(shares
                .map { s -> Pair(s.subjectId.asString(), EmbeddableFraction(s.fraction)) }
                .toMap()
        )
    }

}

@Embeddable
data class EmbeddableFraction(val numerator: Int, val denominator: Int) {
    constructor(fraction: Fraction) : this(fraction.numerator, fraction.denominator)
    constructor() : this(-1, -1)
}

interface ObjectRightsViewRepository : JpaRepository<ObjectRightsView, String>

@Component
class ObjectRightsProjector(private val repository: ObjectRightsViewRepository) {

    @EventHandler
    fun on(event: ObjectCreatedEvent) {
        val objectRightsView = ObjectRightsView(event.objectId.asString())
        repository.save(objectRightsView)
    }

    @EventHandler
    fun on(event: OwnershipCreatedEvent) {
        repository.findById(event.objectId.asString()).ifPresent { view ->
            view.updateOwnershipId(event.rightId)
            view.updateOwnershipShares(event.shares)
        }
    }

    @EventHandler
    fun on(event: OwnershipTransferredEvent) {
        repository.findById(event.objectId.asString()).ifPresent { view ->
            view.updateOwnershipId(event.rightId)
            view.updateOwnershipShares(event.buyingSubjects)
        }
    }

    @QueryHandler
    fun handle(query: ObjectRightsQuery): ObjectRightsView {
        return repository.findById(query.objectId.asString()).orElse(null)
    }

}
