package nl.kadaster.land_administration.query.identifiers

import nl.kadaster.land_administration.core.commons.ObjectIdentifier
import nl.kadaster.land_administration.core.event.ObjectCreatedEvent
import nl.kadaster.land_administration.core.event.RightCreated
import nl.kadaster.land_administration.core.event.SubjectCreatedEvent
import nl.kadaster.land_administration.query.identifiers.api.LatestIdentifiers
import nl.kadaster.land_administration.query.identifiers.api.LatestObjectIdLocalId
import nl.kadaster.land_administration.query.identifiers.api.LatestRightIdLocalId
import nl.kadaster.land_administration.query.identifiers.api.LatestSubjectIdLocalId
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.lang.String.format
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import kotlin.math.max

@Entity
data class IdentifierView(
        @Id val identifierType: String,
        var value: String = format("%015d", 0)
) {
    constructor() : this("identifier")
}

interface IdentifierViewRepository : JpaRepository<IdentifierView, String>

@Component
class IdentifierProjector(private val repository: IdentifierViewRepository) {

    private fun addOrUpdateView(id: ObjectIdentifier) {
        repository.findById(id.namespace).ifPresentOrElse({ view ->
            view.value = max(id.localId, view.value)
        }, {
            repository.save(IdentifierView(id.namespace, id.localId))
        })
    }

    private fun max(localId1: String, localId2: String): String {
        val maxValue = max(localId1.toLong(), localId2.toLong())
        return format("%015d", maxValue)
    }

    @EventHandler
    fun on(event: ObjectCreatedEvent) {
        addOrUpdateView(event.objectId)
    }

    @EventHandler
    fun on(event: RightCreated) {
        addOrUpdateView(event.right.rightId)
    }

    @EventHandler
    fun on(event: SubjectCreatedEvent) {
        addOrUpdateView(event.subject)
    }

    @QueryHandler
    fun handle(query: LatestIdentifiers): MutableList<IdentifierView> {
        return repository.findAll()
    }

    @QueryHandler
    fun handle(query: LatestObjectIdLocalId): Optional<String>? {
        return repository.findById(query.type).map { it.value }
    }

    @QueryHandler
    fun handle(query: LatestRightIdLocalId): Optional<String>? {
        return repository.findById(query.type).map { it.value }
    }

    @QueryHandler
    fun handle(query: LatestSubjectIdLocalId): Optional<String>? {
        return repository.findById(query.type).map { it.value }
    }

}
