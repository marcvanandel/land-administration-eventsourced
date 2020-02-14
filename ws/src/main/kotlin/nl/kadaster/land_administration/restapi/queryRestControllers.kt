package nl.kadaster.land_administration.restapi

import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.query.identifiers.IdentifierView
import nl.kadaster.land_administration.query.identifiers.LatestIdentifiers
import nl.kadaster.land_administration.query.ladm.ObjectRightsQuery
import nl.kadaster.land_administration.query.ladm.ObjectRightsView
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.LoggerFactory
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

//@Profile("query-ladm")
@RestController
@RequestMapping("/objects")
class QueryObjectController(
        private val queryGateway: QueryGateway) {

    private val logger = LoggerFactory.getLogger(QueryObjectController::class.java)

    @GetMapping("/{objectId}")
    fun findObjectByLocalId(@PathVariable("objectId") objectId: String): CompletableFuture<ObjectRightsView> {
        return queryGateway.query(
                ObjectRightsQuery(ObjectId(objectId)),
                ResponseTypes.instanceOf(ObjectRightsView::class.java)).also { it.whenComplete { t, u -> logger.info("rest result: {}", t) } }
    }

}

@RestController
class RootController() {

    @GetMapping("/")
    fun index(model: Model): String {
        model["title"] = "It works!"
        return "index"
    }

}

//@Profile("query")
@RestController
class IdentifierController(
        private val queryGateway: QueryGateway) {

    private val logger = LoggerFactory.getLogger(RootController::class.java)

    @GetMapping("/identifiers")
    fun getLatestIdentifiers(): List<Map<*, *>> {
        val query = queryGateway.query(
                LatestIdentifiers(),
                ResponseTypes.multipleInstancesOf(IdentifierView::class.java)).get()
        logger.info("query result: {}", query)
        @Suppress("UNCHECKED_CAST")
        return query as List<Map<*, *>>
    }

}
