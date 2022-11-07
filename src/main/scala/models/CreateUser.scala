package models

import zio.json.JsonCodec
import zio.json.DeriveJsonCodec

final case class CreateUser(name: String)
object CreateUser {
  implicit val codec: JsonCodec[CreateUser] =
    DeriveJsonCodec.gen[CreateUser]
}
