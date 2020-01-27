package nl.kadaster.land_administration

import nl.kadaster.land_administration.api.ObjectController
import nl.kadaster.land_administration.api.SubjectController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener


@SpringBootApplication
class LandAdministrationApplication(
        private val objectController: ObjectController,
        private val subjectController: SubjectController) {

    @EventListener(ApplicationReadyEvent::class)
    fun doSomethingAfterStartup() {
        println("hello world, I have just started up")

        objectController.createObject()
        subjectController.createSubject()
    }
}

fun main(args: Array<String>) {
    runApplication<LandAdministrationApplication>(*args) {

    }
}
