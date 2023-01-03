package services

import zio.{Task, ZIO}
import models.User
import models.CreateUser

import java.util.UUID

trait UserService {
  def getAll: Task[List[User]]
  def addUser(user: CreateUser): Task[User]
  def get(id: UUID): Option[User]
}
