package models

import java.util.UUID
import zio.json.JsonCodec
import zio.json.DeriveJsonCodec

sealed trait UserRequest
object UserRequest {
  final case class Get(id: UUID) extends UserRequest
  final case class GetAll() extends UserRequest
  final case class Create(user: CreateUser) extends UserRequest

  implicit val codec: JsonCodec[UserRequest] =
    DeriveJsonCodec.gen[UserRequest]
}
