import scala.meta.parsers.Parsed.Error.apply
import zhttp.http.Response
import zhttp.http.Status
import zhttp.http.Body

sealed trait HttpError
object HttpError:
  sealed trait BadRequest extends HttpError
  object BadRequest:
    case object BadJson extends BadRequest

  extension (error: HttpError)
    def toResponse = error match {
      case BadRequest.BadJson =>
        Response.apply(
          status = Status.BadRequest,
          body = Body.fromString("Bad Json")
        )
    }
