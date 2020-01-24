package nl.kadaster.land_administration.api

import nl.kadaster.land_administration.command.ObjectIdGenerator
import nl.kadaster.land_administration.command.SubjectIdGenerator
import nl.kadaster.land_administration.coreapi.CreateObjectCommand
import nl.kadaster.land_administration.coreapi.CreateSubjectCommand
import nl.kadaster.land_administration.coreapi.ObjectRightsQuery
import nl.kadaster.land_administration.query.ObjectRightsView
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class ObjectController(
        private val commandGateway: CommandGateway,
        private val queryGateway: QueryGateway) {

    @PostMapping("/objects/create")
    fun createObject() {
        val objectId = ObjectIdGenerator.next()
        commandGateway.send<CreateObjectCommand>(CreateObjectCommand(objectId))
        println("subject [id: $objectId] created (command send)")
    }

    @GetMapping("/objects/{objectId}")
    fun findObjectByLocalId(@PathVariable("objectId") objectId: Long): CompletableFuture<ObjectRightsView>? {
        return queryGateway.query(
                ObjectRightsQuery(objectId),
                ResponseTypes.instanceOf(ObjectRightsView::class.java))
    }

    @GetMapping("/")
    fun index(model: Model): String {
        model["title"] = "It works!"
        return "index"
    }

}

@RestController
class SubjectController(private val commandGateway: CommandGateway) {

    @PostMapping("/subjects/create")
    fun createSubject() {
        val subjectId = SubjectIdGenerator.next()
        commandGateway.send<CreateSubjectCommand>(CreateSubjectCommand(subjectId))
        println("subject [id: $subjectId] created (command send)")
    }

}
