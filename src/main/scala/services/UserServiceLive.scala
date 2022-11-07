package services

import java.util.UUID
import models._
import collection.mutable.Map
import zio._
import zio.ZLayer.FunctionConstructor.WithOut

final case class UserServiceLive() extends UserService {

  val uuid = UUID.randomUUID()
  val user = User(uuid, "jens madsen")

  val userMap: scala.collection.mutable.Map[UUID, User] = Map(user.id -> user)

  override def getAll: ZIO[Any, Throwable, List[User]] =
    ZIO.succeed(userMap.values.toList)

  override def addUser(user: CreateUser): ZIO[Any, Throwable, User] = {
    val newUuid = UUID.randomUUID()
    val newUser = User(newUuid, user.name)
    userMap.put(newUuid, newUser)
    ZIO.succeed(newUser)
  }

  override def get(id: UUID) =
    ZIO.fromOption(userMap.get(id) match {
      case Some(user) => Some(user)
      case _          => None
    })
}

object UserServiceLive {
  val layer: URLayer[Any, UserService] =
    ZLayer.fromFunction(UserServiceLive.apply _)
}
