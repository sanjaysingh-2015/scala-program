package controllers

import daos.ActorDao
import models.Actor
import play.api.libs.json.{JsError, JsValue, Json, Reads, Writes}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ActorController @Inject()(
                               cc: ControllerComponents,
                               actorDao: ActorDao)
                               (implicit executionContext: ExecutionContext)
extends AbstractController(cc){
  implicit val actorReads: Reads[Actor] = Json.reads[Actor]
  implicit val actorWrites: Writes[Actor] = Json.writes[Actor]

  def getAllActors: Action[AnyContent] = Action.async { implicit request =>
    actorDao.getAll().map(actors => Ok(Json.toJson(actors)))
  }

  def getActorById(id: Long): Action[AnyContent] = Action.async { implicit request =>
    actorDao.getById(id).map {
      case Some(actor) => Ok(Json.toJson(actor))
      case None => NotFound
    }
  }

  def createActor: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[Actor].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      actor => actorDao.createActor(actor.name).map(createdActor => {
        println(s"Actor created successfully with name: ${actor.name}")
        Created(Json.toJson(createdActor))
      })
    )
  }

  def updateActor(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[Actor].fold(
      errors => Future.successful(BadRequest("Invalid data")),
      actor => actorDao.updateActor(id, actor.name).map {
        case Some(updatedActor) => Ok(Json.toJson(updatedActor))
        case None => NotFound
      }
    )
  }

  def deleteActor(id: Long): Action[AnyContent] = Action.async { implicit request =>
    actorDao.delete(id).map {
      case true => NoContent
      case false => NotFound
    }
  }
}
