package models

import zio.json.JsonCodec
import zio.json.DeriveJsonCodec

sealed trait UserResponse
object UserResponse {
  final case class Got(user: User) extends UserResponse
  final case class GotAll(users: List[User]) extends UserResponse
  final case class Created(user: User) extends UserResponse

  implicit val codec: JsonCodec[UserResponse] =
    DeriveJsonCodec.gen[UserResponse]
}
