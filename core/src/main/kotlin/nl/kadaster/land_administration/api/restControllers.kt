package nl.kadaster.land_administration.api

import nl.kadaster.land_administration.coreapi.*
import nl.kadaster.land_administration.query.ObjectRightsProjector
import nl.kadaster.land_administration.query.ObjectRightsView
import nl.kadaster.land_administration.query.SubjectProjector
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
        private val queryGateway: QueryGateway,
        private val objRightsProjector: ObjectRightsProjector) {

    @PostMapping("/objects/create")
    fun createObject() {
        val objectId = ObjectId(objRightsProjector.handle(MaxObjectId()) + 1)
        commandGateway.send<CreateObjectCommand>(CreateObjectCommand(objectId))
        println("object [id: $objectId] created (command send)")
    }

    @PostMapping("/objects/{objectId}/transferOwnership/from/{sellingSubjectId}/to/{buyingSubjectId}")
    fun transferOwnershipFromTo(
            @PathVariable("objectId") objectId: Long,
            @PathVariable("sellingSubjectId") sellingSubjectId: Long,
            @PathVariable("buyingSubjectId") buyingSubjectId: Long) {

        findObjectByLocalId(objectId).thenApply { view ->
            if (view.ownershipShares.isEmpty()) {
                commandGateway.send<CreateOwnershipCommand>(CreateOwnershipCommand(
                        objectId = ObjectId(objectId),
                        owners = setOf(Share(SubjectId(buyingSubjectId), Fraction(1, 1)))
                ))
                println("Command for ownership creation posted")
            } else {
                commandGateway.send<TransferOwnerShipCommand>(TransferOwnerShipCommand(
                        objectId = ObjectId(objectId),
                        sellingShare = Share(SubjectId(sellingSubjectId), Fraction(1, 1)),
                        buyingSubjects = setOf(Share(SubjectId(buyingSubjectId), Fraction(1, 1)))
                ))
                println("Command for ownership transfer posted")
            }

        }


//        findObjectByLocalId(objectId).thenApply { view ->
//            when {
//                sellingSubjectId == null && view.ownershipShares.isNotEmpty() ->
//                    throw DomainException("There are shares already so selling subject cannot be null")
//
//                sellingSubjectId != null && !view.ownershipShares.containsKey(sellingSubjectId) ->
//                    throw DomainException("Selling subject [id: $sellingSubjectId] does not own any shares")
//
//                sellingSubjectId == null && view.ownershipShares.isEmpty() -> {
//                    AggregateLifecycle.apply(
//                            OwnershipCreatedEvent(
//                                    ObjectId(objectId),
//                                    RightId(objectId),
//                                    setOf(Share(SubjectId(buyingSubjectId), Fraction(1, 1)))))
//                }
//
//                sellingSubjectId != null && view.ownershipShares.containsKey(sellingSubjectId) -> {
//                    AggregateLifecycle.apply(
//                            OwnershipTransferredEvent(
//                                    ObjectId(objectId),
//                                    RightId(objectId),
//                                    Share(SubjectId(sellingSubjectId), Fraction(1, 1)),
//                                    setOf(Share(SubjectId(buyingSubjectId), Fraction(1, 1))))
//                    )
//                }
//                else ->
//                    throw DomainException("Unexpected situation occurred!")
//            }

    }

    @GetMapping("/objects/{objectId}")
    fun findObjectByLocalId(@PathVariable("objectId") objectId: Long): CompletableFuture<ObjectRightsView> {
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
class SubjectController(
        private val commandGateway: CommandGateway,
        private val subjectProjector: SubjectProjector) {

    @PostMapping("/subjects/create")
    fun createSubject() {
        val subjectId = SubjectId(subjectProjector.handle(MaxSubjectId()) + 1)
        commandGateway.send<CreateSubjectCommand>(CreateSubjectCommand(subjectId))
        println("subject [id: $subjectId] created (command send)")
    }

}
