package controllers

import payloads.{UserRequest, UserResponse}
import payloads.common.{ApiError, ApiSuccess, StatusUpdatePayload}
import play.api.libs.json.{JsError, JsValue, Json, Reads, Writes}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.UserService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserController @Inject()(cc: ControllerComponents,
                               userService: UserService)
                              (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {
  implicit val apiSuccessReads: Reads[ApiSuccess] = Json.reads[ApiSuccess]
  implicit val apiErrorReads: Reads[ApiError] = Json.reads[ApiError]
  implicit val statusUpdatePayloadReads: Reads[StatusUpdatePayload] = Json.reads[StatusUpdatePayload]
  implicit val UserRequestReads: Reads[UserRequest] = Json.reads[UserRequest]
  implicit val UserResponseReads: Reads[UserResponse] = Json.reads[UserResponse]
  implicit val apiSuccessWrites: Writes[ApiSuccess] = Json.writes[ApiSuccess]
  implicit val apiErrorWrites: Writes[ApiError] = Json.writes[ApiError]
  implicit val statusUpdatePayloadWrites: Writes[StatusUpdatePayload] = Json.writes[StatusUpdatePayload]
  implicit val UserRequestWrites: Writes[UserRequest] = Json.writes[UserRequest]
  implicit val UserResponseWrites: Writes[UserResponse] = Json.writes[UserResponse]

  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[UserRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      user => userService.create(user).map(user => {
        val response = ApiSuccess(Json.toJson(user), "201:Created", s"User Created Successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def update(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[UserRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      user => userService.update(id, user).map(user => {
        val response = ApiSuccess(Json.toJson(user), "201:Created", s"User Updated Successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def statusUpdate(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[StatusUpdatePayload].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      status => userService.statusUpdate(id, status).map(status => {
        val response = ApiSuccess(Json.toJson(status), "201:Created", s"User Status Updated Successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def delete(id: Long):Action[AnyContent] = Action.async { implicit request =>
    userService.delete(id).map { status =>
      val response = ApiSuccess(Json.toJson(status), "201:Created", s"User Deleted Successfully")
      Created(Json.toJson(response))
    }
  }

  def findByFilter(id: Option[Long], userName: Option[String], name: Option[String], email: Option[String], phoneNo: Option[String]):Action[AnyContent] = Action.async { implicit request =>
    (id, userName, name, email, phoneNo) match {
      case (Some(i), _, _, _, _) =>
        userService.getById(i).map { users =>
            val response = ApiSuccess(Json.toJson(users), "200:Fetch", s"User fetched Successfully for id: ${i}")
            Ok(Json.toJson(response))
        }
      case (_, Some(un), _, _, _) =>
        userService.getByUserName(un).map { users =>
          val response = ApiSuccess(Json.toJson(users), "200:Fetch", s"User fetched Successfully for userName: ${un}")
          Ok(Json.toJson(response))
        }
      case (_, _, Some(n), _, _) =>
        userService.getByName(n).map { users =>
          val response = ApiSuccess(Json.toJson(users), "200:Fetch", s"User fetched Successfully for Name: ${n}")
          Ok(Json.toJson(response))
        }
      case (_, _, _, Some(e), _) =>
        userService.getByEmail(e).map { users =>
          val response = ApiSuccess(Json.toJson(users), "200:Fetch", s"User fetched Successfully for userName: ${e}")
          Ok(Json.toJson(response))
        }
      case (_, _, _, _, Some(p)) =>
        userService.getByPhoneNo(p).map { users =>
          val response = ApiSuccess(Json.toJson(users), "200:Fetch", s"User fetched Successfully for Phone No: ${p}")
          Ok(Json.toJson(response))
        }
      case _ =>
        userService.getAll().map { users =>
          val response = ApiSuccess(Json.toJson(users), "200:Fetch", s"User fetched Successfully")
          Ok(Json.toJson(response))
        }
    }
  }

}
