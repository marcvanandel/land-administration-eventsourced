package nl.kadaster.land_administration

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener


@SpringBootApplication
class LandAdministrationApplication {

    @EventListener(ApplicationReadyEvent::class)
    fun doSomethingAfterStartup() {
        println("hello world, I have just started up")
    }
}

fun main(args: Array<String>) {
    runApplication<LandAdministrationApplication>(*args) {

    }
}
