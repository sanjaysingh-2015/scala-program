package controllers

import payloads.{SkillRequest, SkillResponse}
import payloads.common.{ApiError, ApiSuccess, StatusUpdatePayload}
import play.api.libs.json.{JsError, JsValue, Json, Reads, Writes}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.SkillService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SkillController @Inject()(cc: ControllerComponents,
                                skillService: SkillService)
                               (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {
  implicit val apiSuccessReads: Reads[ApiSuccess] = Json.reads[ApiSuccess]
  implicit val apiErrorReads: Reads[ApiError] = Json.reads[ApiError]
  implicit val statusUpdatePayloadReads: Reads[StatusUpdatePayload] = Json.reads[StatusUpdatePayload]
  implicit val skillRequestReads: Reads[SkillRequest] = Json.reads[SkillRequest]
  implicit val skillResponseReads: Reads[SkillResponse] = Json.reads[SkillResponse]

  implicit val apiSuccessWrites: Writes[ApiSuccess] = Json.writes[ApiSuccess]
  implicit val apiErrorWrites: Writes[ApiError] = Json.writes[ApiError]
  implicit val statusUpdatePayloadWrites: Writes[StatusUpdatePayload] = Json.writes[StatusUpdatePayload]
  implicit val skillRequestWrites: Writes[SkillRequest] = Json.writes[SkillRequest]
  implicit val skillResponseWrites: Writes[SkillResponse] = Json.writes[SkillResponse]

  def create(): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[SkillRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      skill => skillService.create(skill).map(skill => {
        val response = ApiSuccess(Json.toJson(skill), "201:Created", s"Created details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def update(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[SkillRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      skill => skillService.update(id, skill).map(skill => {
        val response = ApiSuccess(Json.toJson(skill), "201:Created", s"Updated details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request =>
    skillService.delete(id).map(status => {
      val response = ApiSuccess(Json.toJson(status), "201:Created", s"Deleted details successfully")
      Created(Json.toJson(response))
    })
  }

  def getByFilter(id: Option[Long], code: Option[String], name: Option[String]): Action[AnyContent] = Action.async { implicit request =>
    (id, code, name) match {
      case (Some(i), _, _) =>
        skillService.getById(i).map {
          case Some(skill) =>
            val response = ApiSuccess(Json.toJson(skill), "200:Fetch", s"Fetch details successfully for id: ${i}")
            Ok(Json.toJson(response))
          case None =>
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for id: ${i}")
            BadRequest(Json.toJson(response))
        }
      case (_, Some(c), _) =>
        skillService.getByCode(c).map {
          case Some(skill) =>
            val response = ApiSuccess(Json.toJson(skill), "200:Fetch", s"Fetch details successfully for code: ${c}")
            Ok(Json.toJson(response))
          case None =>
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for code: ${c}")
            BadRequest(Json.toJson(response))
        }
      case (_, _, Some(n)) =>
        skillService.getByName(n).map {
          case Some(skill) =>
            val response = ApiSuccess(Json.toJson(skill), "200:Fetch", s"Fetch details successfully for name: ${n}")
            Ok(Json.toJson(response))
          case None =>
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for name: ${n}")
            BadRequest(Json.toJson(response))
        }
      case _ =>
        skillService.getAll.map { skills =>
          val response = ApiSuccess(Json.toJson(skills), "200:Fetch", s"Fetched all details successfully")
          Ok(Json.toJson(response))
        }
    }
  }
}
