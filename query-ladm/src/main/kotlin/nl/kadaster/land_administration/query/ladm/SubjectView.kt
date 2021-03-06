package nl.kadaster.land_administration.query.ladm

import nl.kadaster.land_administration.core.event.SubjectCreatedEvent
import org.axonframework.eventhandling.EventHandler
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import javax.persistence.Entity
import javax.persistence.Id

@Profile("query-ladm")
@Entity
data class SubjectView(
        @Id val subjectId: String) {

    constructor() : this("undefined")
}

@Profile("query-ladm")
interface SubjectViewRepository : JpaRepository<SubjectView, String>

@Profile("query-ladm")
@Component
class SubjectProjector(private val repository: SubjectViewRepository) {

    @EventHandler
    fun on(event: SubjectCreatedEvent) {
        val subjectView = SubjectView(event.subject.localId)
        repository.save(subjectView)
    }

}
