package models

import java.util.UUID
import zio.json.JsonDecoder
import zio.json.DeriveJsonEncoder
import zio.json.DeriveJsonDecoder
import zio.json.JsonEncoder

final case class User(id: UUID, name: String)
object User {
  implicit val decoder: JsonDecoder[User] = DeriveJsonDecoder.gen[User]
  implicit val encoder: JsonEncoder[User] = DeriveJsonEncoder.gen[User]
}
