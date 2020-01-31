package nl.kadaster.land_administration.query.ladm

import nl.kadaster.land_administration.core.events.SubjectCreatedEvent
import org.axonframework.eventhandling.EventHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class SubjectView(
        @Id val subjectId: String) {

    constructor() : this("undefined")
}

interface SubjectViewRepository : JpaRepository<SubjectView, String> {
    @Query("select max(i.subjectId) from SubjectView as i")
    fun maxSubjectId(): Long?
}

@Component
class SubjectProjector(private val repository: SubjectViewRepository) {

    @EventHandler
    fun on(event: SubjectCreatedEvent) {
        val subjectView = SubjectView(event.subject.localId)
        repository.save(subjectView)
    }

}
