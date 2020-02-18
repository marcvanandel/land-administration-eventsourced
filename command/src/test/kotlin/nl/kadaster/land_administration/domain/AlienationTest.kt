package nl.kadaster.land_administration.domain

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec

class AlienationTest : BehaviorSpec({
    Given("initial state expressed in events") {
        // TODO create parcel events with a right for 1 subject
        When("an alienation command is received") {
            //      val alienation = new Alienation()
            // TODO commandGateway.post(alienation)
            Then("assert expected event(s)") {
                // TODO expectedEvents should contain new RightTransferedEvent()

            }
        }
    }
})

