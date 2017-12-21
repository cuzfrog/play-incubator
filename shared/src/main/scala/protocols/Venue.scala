package protocols

import play.api.libs.json.{Json, OWrites}

case class Venue(name: String, lon: Double, lat: Double, id: Long)
object Venue{
  implicit val jsonWrite: OWrites[Venue] = Json.writes[Venue]
}
