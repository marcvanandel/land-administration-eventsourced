package nl.kadaster.land_administration

import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.query.identifiers.IdentifierView
import nl.kadaster.land_administration.query.identifiers.api.LatestIdentifiers
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
class RootController(private val environment: Environment,
                     private val queryGateway: QueryGateway) {

    private val logger: Logger = LoggerFactory.getLogger(RootController::class.java)

    @GetMapping(value = ["/", "/index"])
    fun index(model: Model): ModelAndView {
        model["title"] = "It works!"
        model["profile"] = environment.activeProfiles.joinToString(",")
        model["objectId"] = getLatestObjectId()
        logger.info("objectId in model: {}", model.getAttribute("objectId"))
        return ModelAndView("index", model.asMap())
    }

    private fun getLatestObjectId(): String {
        val query = queryGateway.query(
                LatestIdentifiers(),
                ResponseTypes.multipleInstancesOf(IdentifierView::class.java)).get()
        logger.info("query result: {}", query)
        @Suppress("UNCHECKED_CAST")
        val latestIdentifiers = (query as List<Map<String, String>>)
                .filter { v -> v["identifierType"] == ObjectId.namespace }
                .getOrNull(0)
        return latestIdentifiers?.get("value") ?: "000000000000001"
    }


}

