package nl.kadaster.land_administration.query

import nl.kadaster.land_administration.coreapi.*
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.util.*
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id

@Entity
data class ObjectRightsView(
        @Id val objectId: Long,
        var ownershipId: Long,
        @ElementCollection(fetch = FetchType.EAGER) val ownershipShares: MutableMap<Long, Double>) {

    constructor() : this(-1)
    constructor(objectId: Long) : this(objectId, -1, Collections.emptyMap())

    fun updateOwnershipId(rightId: RightId) {
        ownershipId = rightId.localId
    }

    fun updateOwnershipShares(shares: Set<Share>) {
        ownershipShares.clear()
        ownershipShares.putAll(shares
                .map { s -> Pair(s.subjectId.localId, s.fraction.decimal) }
                .toMap()
        )
    }

}

interface ObjectRightsViewRepository : JpaRepository<ObjectRightsView, Long>

@Component
class ObjectRightsProjector(private val repository: ObjectRightsViewRepository) {

    @EventHandler
    fun on(event: ObjectCreatedEvent) {
        val objectRightsView = ObjectRightsView(event.objectId.localId)
        repository.save(objectRightsView)
    }

    @EventHandler
    fun on(event: OwnershipCreatedEvent) {
        repository.findById(event.objectId.localId).ifPresent { view ->
            view.updateOwnershipId(event.rightId)
            view.updateOwnershipShares(event.shares)
        }
    }

    @QueryHandler
    fun handle(query: ObjectRightsQuery): ObjectRightsView {
        return repository.findById(query.objectId).orElse(null)
    }

}
