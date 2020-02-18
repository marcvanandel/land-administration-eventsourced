package nl.kadaster.land_administration.query.ladm

import nl.kadaster.land_administration.core.commons.RightType
import nl.kadaster.land_administration.core.event.ObjectCreatedEvent
import nl.kadaster.land_administration.core.event.RightCreated
import nl.kadaster.land_administration.core.event.RightTransferred
import org.axonframework.eventhandling.EventHandler
import org.axonframework.queryhandling.QueryHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Profile("query-ladm")
@Entity(name = "Object")
@Table(name = "objects_table")
data class ObjectRightsView(
        @Id val objectId: String,
        var ownershipId: String,
        @OneToMany(
                cascade = [CascadeType.ALL],
                fetch = FetchType.EAGER,
                orphanRemoval = true
        ) val rights: MutableSet<Right>) {

    constructor() : this("undefined")
    constructor(objectId: String) : this(objectId, "undefined", Collections.emptySet())

}

@Suppress("JpaAttributeMemberSignatureInspection")
@Embeddable
@Access(AccessType.FIELD)
data class EmbeddableFraction(val numerator: Int, val denominator: Int) {
    constructor() : this(-1, -1)
}

@Profile("query-ladm")
interface ObjectRightsViewRepository : JpaRepository<ObjectRightsView, String>

@Profile("query-ladm")
@Component
class ObjectRightsProjector(private val repository: ObjectRightsViewRepository) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @EventHandler
    fun on(event: ObjectCreatedEvent) {
        val objectRightsView = ObjectRightsView(event.objectId.asString())
        repository.save(objectRightsView)
    }

    @EventHandler
    fun on(event: RightCreated) {
        repository.findById(event.objectId.asString()).ifPresent { view ->
            view.ownershipId = event.right.rightId.asString()

            val r = Right(
                    description = "The ownership of this parcel",
                    rightId = event.right.rightId.asString(),
                    party = event.right.party.asString(),
                    share = EmbeddableFraction(event.right.share.numerator, event.right.share.denominator),
                    shareCheck = false,
                    timeSpec = LocalDateTime.now(),
                    type = RightType.OWNERSHIP
            )
            view.rights.add(r)
            logger.debug("added right: {}", r)
        }
        repository.findById(event.objectId.asString()).ifPresent { view ->
            logger.debug("double check view: {}", view)
        }
    }

    @EventHandler
    fun on(event: RightTransferred) {
        repository.findById(event.objectId.asString()).ifPresent { view ->
            view.ownershipId = event.buyingRight.rightId.asString()

            val curRight = view.rights.first { r -> r.rightId == event.sellingRight.asString() }
            val newRight = Right(
                    description = "The ownership of this parcel",
                    rightId = event.buyingRight.rightId.asString(),
                    party = event.buyingRight.party.asString(),
                    share = EmbeddableFraction(event.buyingRight.share.numerator, event.buyingRight.share.denominator),
                    shareCheck = false,
                    timeSpec = LocalDateTime.now(),
                    type = RightType.OWNERSHIP
            )
            logger.debug("replacing right [{}] with right [{}]", curRight, newRight)
            view.rights.remove(curRight)
            view.rights.add(newRight)
        }
    }

    @QueryHandler
    fun handle(query: ObjectRightsQuery): ObjectRightsView {
        val view = repository.findById(query.objectId.asString()).orElse(null)
        logger.debug("double check view: {}", view)
        return view
    }

}
