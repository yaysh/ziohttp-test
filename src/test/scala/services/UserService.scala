package services

import zio.test._
import zio.test.Assertion.*
import models.CreateUser
import zio.Scope

object UserServiceSpec extends ZIOSpecDefault {

  override def spec: Spec[TestEnvironment, Throwable] = {
    suite("UserServiceSpec")(
      suite("added owner does not exist in db")(
        for {
          owner <- UserServiceLive.create
        }
      )
    ) @@ DbMigrationAspect.migrateOnce()() @@ TestAspect.withLiveRandom
  }
    .provideShared(
      UserServiceLive.layer,
      ZPostgresSQLContainer.Settings.default,
      ZPostgresSQLContainer.live,
      TestContainerLayers.dataSourceLayer,
      Live.default,
      ZENv.live
    )
}
