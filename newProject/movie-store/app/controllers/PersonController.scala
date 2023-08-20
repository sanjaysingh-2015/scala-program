package controllers

import akka.stream.impl.Stages.DefaultAttributes.recover
import dao.PersonDAO
import play.api.libs.json.Json
import play.api.mvc.Results.Ok
import play.api.mvc._
import play.mvc.BodyParser.AnyContent
import play.mvc.{Action, Controller}

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class UserController @Inject()(val personDAO: PersonDAO)(implicit ec: ExecutionContext) extends Controller {
  def list = Action.async {
    personDAO.getAll().map { persons =>
      Ok(Json.toJson(persons))
    }.recover(recover)
  }


  // Implement other CRUD APIs (create, update, delete)
}
