package nl.kadaster.land_administration.restapi

import nl.kadaster.land_administration.command.api.CreateObjectCommand
import nl.kadaster.land_administration.command.api.CreateOwnershipCommand
import nl.kadaster.land_administration.command.api.CreateSubjectCommand
import nl.kadaster.land_administration.command.api.TransferOwnerShipCommand
import nl.kadaster.land_administration.command.util.IdentifierGenerator
import nl.kadaster.land_administration.core.commons.Fraction
import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.Share
import nl.kadaster.land_administration.core.commons.SubjectId
import nl.kadaster.land_administration.query.ladm.ObjectRightsQuery
import nl.kadaster.land_administration.query.identifiers.IdentifierView
import nl.kadaster.land_administration.query.identifiers.LatestIdentifiers
import nl.kadaster.land_administration.query.ladm.ObjectRightsView
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.slf4j.LoggerFactory
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.*
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/objects")
class ObjectController(
        private val commandGateway: CommandGateway,
        private val queryGateway: QueryGateway,
        private val identifierGenerator: IdentifierGenerator) {

    private val logger = LoggerFactory.getLogger(ObjectController::class.java)

    @PostMapping("/create")
    fun createObject() {
        val objectId = identifierGenerator.nextObjectId()
        commandGateway.send<CreateObjectCommand>(CreateObjectCommand(objectId))

        logger.info("object [id: {}] created (command send)", objectId)
    }

    @PostMapping("/{objectId}/transferOwnership/from/{sellingSubjectId}/to/{buyingSubjectId}")
    fun transferOwnershipFromTo(
            @PathVariable("objectId") objectId: String,
            @PathVariable("sellingSubjectId") sellingSubjectId: String,
            @PathVariable("buyingSubjectId") buyingSubjectId: String) {

        try {

            findObjectByLocalId(objectId).thenApply { view ->
                if (view.ownershipShares.isEmpty()) {
                    val command = CreateOwnershipCommand(
                            objectId = ObjectId(objectId),
                            owners = setOf(Share(SubjectId(buyingSubjectId), Fraction(1, 1))))
                    commandGateway.send<String>(command)
                    logger.debug("Command for ownership creation posted: {}", command)
                } else {
                    val command = TransferOwnerShipCommand(
                            objectId = ObjectId(objectId),
                            sellingShare = Share(SubjectId(sellingSubjectId), Fraction(1, 1)),
                            buyingSubjects = setOf(Share(SubjectId(buyingSubjectId), Fraction(1, 1))))
                    commandGateway.send<String>(command)
                    logger.debug("Command for ownership transfer posted: {}", command)
                }
            }
        } catch (e: Exception) {
            logger.warn("caught exception: $e")
        }
    }

    @GetMapping("/{objectId}")
    fun findObjectByLocalId(@PathVariable("objectId") objectId: String): CompletableFuture<ObjectRightsView> {
        return queryGateway.query(
                ObjectRightsQuery(ObjectId(objectId)),
                ResponseTypes.instanceOf(ObjectRightsView::class.java))
    }

}

@RestController
@RequestMapping("/subjects")
class SubjectController(
        private val commandGateway: CommandGateway,
        private val identifierGenerator: IdentifierGenerator) {

    private val logger = LoggerFactory.getLogger(SubjectController::class.java)

    @PostMapping("/create")
    fun createSubject() {
        val subjectId = identifierGenerator.nextSubjectId()
        commandGateway.send<CreateSubjectCommand>(CreateSubjectCommand(subjectId))
        logger.info("subject [id: {}] created (command send)", subjectId)
    }

}

@RestController
class RootController(
        private val queryGateway: QueryGateway) {

    private val logger = LoggerFactory.getLogger(RootController::class.java)

    @GetMapping("/")
    fun index(model: Model): String {
        model["title"] = "It works!"
        return "index"
    }

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
