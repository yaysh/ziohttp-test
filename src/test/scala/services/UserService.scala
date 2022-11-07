package services

import zio.test._
import zio.test.Assertion.*
import models.CreateUser
import zio.Scope

object UserServiceSpec extends ZIOSpecDefault {

  override def spec = {
    suite("UserServiceSpec")(
      test("add user creates valid uuid") {
        val userService = UserServiceLive()
        for {
          newUser <- userService.addUser(CreateUser("hello"))
        } yield assertTrue(newUser._1.toString.nonEmpty)
      }
    ).provideLayer(UserServiceLive.layer)
  }

}
