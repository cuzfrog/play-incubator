package models

import javax.inject._

import play.api.mvc._
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink}
import akka.util.ByteString
import com.google.inject.ImplementedBy
import play.api.Logger
import play.api.libs.json.{JsString, JsValue}
import play.api.libs.ws._

import scala.concurrent.duration.Duration


trait StreamApi {

  def commence: Boolean

  //def count:Int
}

@Singleton
private class StreamImpl @Inject()(ws: WSClient)
                                  (implicit executionContext: NetworkExecutionContext,
                                   materializer: Materializer) extends StreamApi {

  override def commence = {

    false
  }
}

