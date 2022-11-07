package server

import services._
import models.UserResponse
import models.UserRequest
import models._
import zhttp.http._
import zio._
import zio.json._
import server._

final case class UserRoutes(service: UserService) {
  val routes: Http[Any, Throwable, Request, Response] =

    val basic =
      Http.collectZIO[Request] { case Method.GET -> !! / "users" =>
        service.getAll.map((users: List[User]) => Response.json(users.toJson))
      }
    val addUserEndpoint =
      Http.collectZIO[UserRequest] { case UserRequest.Create(user) =>
        for {
          newUser <- service.addUser(user)
          userResponse <- ZIO.succeed(UserResponse.Created(newUser))
        } yield (userResponse)
      }

    val getUserEndpoint =
      Http.collectZIO[UserRequest] { case UserRequest.Get(id) =>
        for {
          userResponse <- service
            .get(id)
            .map(UserResponse.Got(_))
            .mapError(s => Throwable("asd"))
        } yield userResponse
      }
    basic ++
      getUserEndpoint @@ ServerUtils.jsonIntercept[UserRequest, UserResponse] ++
      addUserEndpoint @@ ServerUtils.jsonIntercept[UserRequest, UserResponse]
}

object UserRoutes {
  val layer: URLayer[UserService, UserRoutes] =
    ZLayer.fromFunction(UserRoutes.apply _)
}

// val userCreateApp: Http[UserService, Throwable, UserRequest, UserResponse] =
//   Http.collectZIO[UserRequest] { case UserRequest.Create(user) =>
//     for {
//       newUser <- UserService.addUser(user)
//       userResponse <- ZIO.succeed(UserResponse.Created(newUser))
//     } yield userResponse
//   }
// val userGetApp =
//   Http.collectZIO[UserRequest] { case UserRequest.Get(id) =>
//     for {
//       userResponse <- UserService
//         .get(id)
//         .map(UserResponse.Got(_))
//         .mapError(s => Throwable("asd"))
//     } yield userResponse
//   }

// val userServiceApp = userGetApp ++ userCreateApp
// val userServiceHttpApp = userServiceApp @@ json[UserRequest, UserResponse]
