import zhttp.http._
import zhttp.service.Server
import zio._
import zio.json._
import server._
import services._

object Main extends ZIOAppDefault {

  // Goals of today:
  // Log each request and response (time it)
  // For each incoming to a specific route, map to json obj
  // For each outgoing map to response

  def run =
    ZIO
      .serviceWithZIO[Controller](_.start)
      .provide(
        Controller.layer,
        UserRoutes.layer,
        UserServiceLive.layer
      )
}
