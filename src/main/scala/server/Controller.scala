package server
import server._
import zhttp.http.middleware._
import zio.json._
import zhttp.http._
import zio._
import server.UserRoutes
import zhttp.service.Server

final case class Controller(
    userRoutes: UserRoutes
) {

  val allRoutes: HttpApp[Any, Throwable] = {
    userRoutes.routes
  }
  // val allRoutes = Http.collectZIO[Request] { case Method.GET -> !! / "users" =>
  //   ZIO.succeed(Response.text("users"))
  // }

  val loggingMiddleware: HttpMiddleware[Any, Nothing] =
    new HttpMiddleware[Any, Nothing] {
      override def apply[R1 <: Any, E1 >: Nothing](
          http: Http[R1, E1, Request, Response]
      ): Http[R1, E1, Request, Response] =
        Http.fromOptionFunction[Request] { request =>
          Random.nextUUID.flatMap { requestId =>
            ZIO.logAnnotate("REQUEST-ID", requestId.toString) {
              for {
                _ <- ZIO.logInfo(s"Request: $request")
                result <- http(request)
              } yield result
            }
          }
        }
    }

  def start: ZIO[Any, Throwable, Unit] =
    for {
      port <- System.envOrElse("PORT", "5000").map(_.toInt)
      _ <- Server.start(
        port,
        allRoutes @@ Middleware.cors() @@ loggingMiddleware
      )
    } yield ()

}

object Controller {
  val layer: ZLayer[UserRoutes, Nothing, Controller] =
    ZLayer.fromFunction(Controller.apply _)
}
