package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class StreamControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting{

  "Stream controller" should{

    "test1" in{
      val controller = inject[StreamController]
      val response = controller.streamTest("http://stream.meetup.com/2/rsvps").apply(FakeRequest())
      status(response) mustBe OK
    }

  }
}
