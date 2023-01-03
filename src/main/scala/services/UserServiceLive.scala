package services

import io.getquill.query

import java.util.UUID
import models.*

import collection.mutable.Map
import zio.*

import javax.sql.DataSource

final case class UserServiceLive(dataSource: DataSource) extends UserService {

  import repositories.QuillContext._

  override def getAll: Task[List[User]] =
    run(query[User]).provideEnvironment(Zenvironment(dataSource))

  override def addUser(user: CreateUser): ZIO[Any, Throwable, User] = {
    for {
      uuid <- UUID.randomUUID()
      user <- User.make(uuid, user.name)
      _ <- run(query[User].insertValue(lift(user))).provideEnvironment(ZEnvironment(dataSource))
    } yield user
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
