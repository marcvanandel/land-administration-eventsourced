package nl.kadaster.land_administration.query.identifiers

import nl.kadaster.land_administration.core.commons.ObjectIdentifier
import nl.kadaster.land_administration.core.events.ObjectCreatedEvent
import nl.kadaster.land_administration.core.events.OwnershipCreatedEvent
import nl.kadaster.land_administration.core.events.SubjectCreatedEvent
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
        var value: String = "00000000000000"
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
    fun on(event: OwnershipCreatedEvent) {
        addOrUpdateView(event.rightId)
    }

    @EventHandler
    fun on(event: SubjectCreatedEvent) {
        addOrUpdateView(event.subject)
    }

    @QueryHandler
    fun handle(query: LatestIdentifiers): MutableList<IdentifierView> {
        val value = repository.findAll()
        println(value)
        return value
    }

    @QueryHandler
    fun handle(query: LatestObjectIdLocalId): Optional<String>? {
        val opt = repository.findById(query.type).map { it.value }
        return opt
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
