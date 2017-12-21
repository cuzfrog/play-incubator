package controllers

import akka.actor._

object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      println(s"received msg from client: $msg")

      out ! ("I received your message: " + msg)
  }
}
