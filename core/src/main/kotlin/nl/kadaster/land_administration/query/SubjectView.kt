package nl.kadaster.land_administration.query

import nl.kadaster.land_administration.coreapi.*
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Component
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class SubjectView(
        @Id val subjectId: Long) {

    constructor() : this(-1)
}

interface SubjectViewRepository : JpaRepository<SubjectView, Long> {
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

    @QueryHandler
    fun handle(query: MaxSubjectId): Long {
        return repository.maxSubjectId() ?: 0
    }

}
