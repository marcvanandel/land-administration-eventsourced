package nl.kadaster.land_administration.restapi

import nl.kadaster.land_administration.command.api.*
import nl.kadaster.land_administration.command.util.IdentifierGenerator
import nl.kadaster.land_administration.core.commons.ObjectId
import nl.kadaster.land_administration.core.commons.SubjectId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile("command")
@RestController
@RequestMapping("/objects")
class CommandObjectController(
        private val commandGateway: CommandGateway,
        private val identifierGenerator: IdentifierGenerator) {

    private val logger = LoggerFactory.getLogger(CommandObjectController::class.java)

    @PostMapping("/create")
    fun createObject() {
        val objectId = identifierGenerator.nextObjectId()
        commandGateway.send<CreateObjectCommand>(CreateObjectCommand(objectId))

        logger.info("object [id: {}] created (command send)", objectId)
    }

    @PostMapping("/{objectId}/createOwnership/{buyingSubjectId}")
    fun createOwnership(
            @PathVariable("objectId") objectId: String,
            @PathVariable("buyingSubjectId") buyingSubjectId: String) {

        try {
            val command = CreateOwnershipCommand(
                    objectId = ObjectId(objectId),
                    owners = setOf(Share(SubjectId(buyingSubjectId), 1, 1)))
            commandGateway.send<String>(command)
            logger.debug("Command for ownership creation posted: {}", command)
        } catch (e: Exception) {
            logger.warn("caught exception: $e")
        }
    }

    @PostMapping("/{objectId}/transferOwnership/from/{sellingSubjectId}/to/{buyingSubjectId}")
    fun transferOwnershipFromTo(
            @PathVariable("objectId") objectId: String,
            @PathVariable("sellingSubjectId") sellingSubjectId: String,
            @PathVariable("buyingSubjectId") buyingSubjectId: String) {

        try {
            val command = TransferOwnerShipCommand(
                    objectId = ObjectId(objectId),
                    sellingShare = Share(SubjectId(sellingSubjectId), 1, 1),
                    buyingSubjects = setOf(Share(SubjectId(buyingSubjectId), 1, 1)))
            commandGateway.send<String>(command)
            logger.debug("Command for ownership transfer posted: {}", command)

        } catch (e: Exception) {
            logger.warn("caught exception: $e")
        }
    }

}

@Profile("command")
@RestController
@RequestMapping("/subjects")
class CommandSubjectController(
        private val commandGateway: CommandGateway,
        private val identifierGenerator: IdentifierGenerator) {

    private val logger = LoggerFactory.getLogger(CommandSubjectController::class.java)

    @PostMapping("/create")
    fun createSubject() {
        val subjectId = identifierGenerator.nextSubjectId()
        commandGateway.send<CreateSubjectCommand>(CreateSubjectCommand(subjectId))
        logger.info("subject [id: {}] created (command send)", subjectId)
    }

}
