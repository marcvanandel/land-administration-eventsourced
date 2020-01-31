package nl.kadaster.land_administration

import nl.kadaster.land_administration.restapi.ObjectController
import nl.kadaster.land_administration.restapi.SubjectController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class LandAdministrationApplication(
        private val objectController: ObjectController,
        private val subjectController: SubjectController)

fun main(args: Array<String>) {
    runApplication<LandAdministrationApplication>(*args) {

    }
}
