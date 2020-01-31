package nl.kadaster.land_administration.query.identifiers

import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.RightId
import nl.kadaster.land_administration.core.commons.SubjectId

class LatestIdentifiers

class LatestObjectIdLocalId {
    val type: String = ObjectId.namespace
}
class LatestRightIdLocalId {
    val type: String = RightId.namespace
}
class LatestSubjectIdLocalId {
    val type: String = SubjectId.namespace
}
