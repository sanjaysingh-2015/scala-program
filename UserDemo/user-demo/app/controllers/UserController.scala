package controllers

import models.User
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, ControllerComponents}
import repositories.UserRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class UserController @Inject()(cc: ControllerComponents, userRepository: UserRepository)
                              (implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def insertUser(): Action[User] = Action.async(parse.json[User]) { implicit request =>
    val user = request.body

    val insertResult: Future[Try[Unit]] = userRepository.createUser(user)

    insertResult.map {
      case Success(_) =>
        Ok(Json.toJson(user)) // Return the inserted user as JSON
      case Failure(exception) =>
        InternalServerError(s"Failed to insert user: ${exception.getMessage}")
    }
  }
}
