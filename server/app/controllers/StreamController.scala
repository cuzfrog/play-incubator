package controllers

import javax.inject.{Inject, Singleton}

import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Sink, Source}
import akka.util.ByteString
import play.api.mvc._
import models.{NetworkExecutionContext, StreamApi}
import play.api.Logger
import play.api.libs.json.{JsString, JsValue, Json}
import play.api.libs.ws.WSClient
import play.api.mvc.WebSocket.MessageFlowTransformer

import scala.concurrent.duration.Duration
import scala.util.Try

@Singleton
class StreamController @Inject()(cc: ControllerComponents, ws: WSClient, streamApi: StreamApi)
                                (implicit executionContext: NetworkExecutionContext,
                                 materializer: Materializer)
  extends AbstractController(cc) {

  private val logger = Logger("Stream")

  private val VenueExtractor = """.*"venue"\:\{"venue_name"\:"(\w\s+)","lon"\:([\d\.]+),"lat"\:([\d\.]+),"venue_id"\:(\d+)\}.*""".r

  private val extractVenue = Flow.fromFunction[ByteString, Option[JsValue]] { bs =>
    val incoming = bs.utf8String
    logger.info(s"Incoming stream: ${incoming.take(10)}...")

    val venueOpt = incoming match {
      case VenueExtractor(name, lon, lat, id) =>
        Option(protocols.Venue(name, lon.toDouble, lat.toDouble, id.toLong))
      case bad =>
        logger.info(s"Incoming stream discarded: ${bad.take(10)}...")
        None
    }
    venueOpt.map(Json.toJson(_))
  }.filter(_.nonEmpty).map(_.get)

  private lazy val streamSource = ws.url("http://stream.meetup.com/2/rsvps/").withMethod("GET")
    .withRequestTimeout(Duration.Inf)
    .stream().map { response =>
    response.bodyAsSource.via(extractVenue)
  }

  private implicit val messageFlowTransformer = MessageFlowTransformer.jsonMessageFlowTransformer[String,JsValue]

  def stream = WebSocket.acceptOrResult[String,JsValue] { requestHeader =>
    val in = Sink.foreach[String](println)

    streamSource.map{ out=>
      Right(Flow.fromSinkAndSource(in, out))
    }
  }

  def streamTest = Action {
    streamSource.foreach { source =>
      source.runWith(Sink.foreach(println))
    }
    Ok("Test stream")
  }
}
