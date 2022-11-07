package server

import zhttp.http.Middleware
import zio.json._
import zhttp.http._
import zio._

object ServerUtils {

  def jsonIntercept[In: JsonDecoder, Out: JsonEncoder]
      : Middleware[Any, Nothing, In, Out, Request, Response] =
    Middleware.codecZIO[Request, Out](
      request =>
        (for {
          bodyAsStr <- request.body.asString
          in <- ZIO
            .fromEither(JsonDecoder[In].decodeJson(bodyAsStr))
            .mapError(msg => new Throwable(msg))
        } yield in).orDie,
      response =>
        ZIO.succeed(Response.text(JsonEncoder[Out].encodeJson(response, None)))
    )
}
