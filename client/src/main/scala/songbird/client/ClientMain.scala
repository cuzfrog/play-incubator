package songbird.client

import scala.scalajs.js
import scalajs.js.timers
import org.scalajs.dom
import songbird.client.component.BasicView
import sri.web.ReactDOM

object ClientMain {
  def main(args: Array[String]): Unit = {
    ReactDOM.render(BasicView(), dom.document.getElementById("app"))
    println("Client render done!")
  }
}
