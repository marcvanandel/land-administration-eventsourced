package nl.marcvanandel.land_administration.domain.model

import nl.marcvanandel.land_administration.command.Alienation
import org.scalatest.{FeatureSpec, GivenWhenThen, ShouldMatchers}

/**
  * Testing all {@link Alienation} command features and scenarios.
  *
  * @author Marc van Andel
  * @since 0.1 on 5-4-2017.
  */
class AlienationTest extends FeatureSpec
  with ShouldMatchers
  with GivenWhenThen {

  feature("first example of alienation (transfer) of ownership on Parcel #1") {

    scenario("transfer from 1 subject to 1 other subject") {

      Given("initial state expressed in events")

      // TODO create parcel events with a right for 1 subject

      When("an alienation command is received")

      val alienation = new Alienation()
      // TODO commandGateway.post(alienation)

      Then("assert expected event(s)")

      // TODO expectedEvents should contain new RightTransferedEvent()

    }

  }

}
