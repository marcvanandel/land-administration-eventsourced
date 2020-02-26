package nl.kadaster.land_administration.command.util

import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.RightId
import nl.kadaster.land_administration.core.commons.SubjectId
import nl.kadaster.land_administration.query.identifiers.api.LatestObjectIdLocalId
import nl.kadaster.land_administration.query.identifiers.api.LatestRightIdLocalId
import nl.kadaster.land_administration.query.identifiers.api.LatestSubjectIdLocalId
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.util.*

@Profile("command")
@Component
class IdentifierGenerator(private val queryGateway: QueryGateway) {

    fun nextObjectId(): ObjectId {
        val latestLocalId = queryGateway
                .query(LatestObjectIdLocalId(), ResponseTypes.optionalInstanceOf(String::class.java)).get()
                .orElseGet { "0" }
        return ObjectId(java.lang.String.format("%015d",  latestLocalId.toLong() + 1))
    }

    fun nextRightId(): RightId {
        return RightId(UUID.randomUUID().toString())
    }

    fun nextSubjectId(): SubjectId {
        val latestLocalId = queryGateway
                .query(LatestSubjectIdLocalId(), ResponseTypes.optionalInstanceOf(String::class.java)).get()
                .orElseGet { "0" }
        return SubjectId(java.lang.String.format("%015d",  latestLocalId.toLong() + 1))

    }

}