package services

import zio.ZIO
import models.User
import models.CreateUser
import java.util.UUID

trait UserService {
  def getAll: ZIO[Any, Throwable, List[User]]
  def addUser(user: CreateUser): ZIO[Any, Throwable, User]
  def get(id: UUID): ZIO[Any, Option[Throwable], User]
}
