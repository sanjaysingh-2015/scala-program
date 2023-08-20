package controllers

import models.AddressType
import payloads.common.{ApiSuccess, ApiError}
import payloads.{AddressTypeRequest, AddressTypeResponse}

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.libs.json.{JsError, JsValue, Json, Reads, Writes}

import scala.concurrent.{ExecutionContext, Future}
import services.AddressTypeService


class AddressTypeController @Inject() (cc: ControllerComponents,
                                       addressTypeService: AddressTypeService)
                                      (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {
  implicit val apiSuccessReads: Reads[ApiSuccess] = Json.reads[ApiSuccess]
  implicit val apiSuccessWrites: Writes[ApiSuccess] = Json.writes[ApiSuccess]
  implicit val apiErrorReads: Reads[ApiError] = Json.reads[ApiError]
  implicit val apiErrorWrites: Writes[ApiError] = Json.writes[ApiError]
  implicit val addressTypeReads: Reads[AddressType] = Json.reads[AddressType]
  implicit val addressTypeWrites: Writes[AddressType] = Json.writes[AddressType]
  implicit val addressTypeRequestReads: Reads[AddressTypeRequest] = Json.reads[AddressTypeRequest]
  implicit val addressTypeRequestWrites: Writes[AddressTypeRequest] = Json.writes[AddressTypeRequest]
  implicit val addressTypeResponseReads: Reads[AddressTypeResponse] = Json.reads[AddressTypeResponse]
  implicit val addressTypeResponseWrites: Writes[AddressTypeResponse] = Json.writes[AddressTypeResponse]

  def createAddressType: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[AddressTypeRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      addressType => addressTypeService.create(addressType).map(createdAddressType => {
        val response = ApiSuccess(Json.toJson(createdAddressType), "201:Created", s"Created details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def updateAddressType(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[AddressTypeRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      addressType => addressTypeService.update(id, addressType).map(createdAddressType => {
        val response = ApiSuccess(Json.toJson(createdAddressType), "201:Created", s"Updated details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request =>
    addressTypeService.delete(id).map( status => {
      val response = ApiSuccess(Json.toJson(status), "200:OK", s"Deleted details successfully")
      Ok(Json.toJson(response))
    })
  }

  def getByFilter(id: Option[Long], code: Option[String], name: Option[String]): Action[AnyContent] = Action.async { implicit request =>
    (id, code, name) match {
      case (Some(i), _, _) =>
        addressTypeService.getById(i).map {
          case Some(addressType) => {
            val response = ApiSuccess(Json.toJson(addressType),"200:OK", s"Fetch details successfully for id: ${i}")
            Ok(Json.toJson(response))
          }
          case None => {
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for id: ${i}")
            BadRequest(Json.toJson(response))
          }
        }

      case (_, Some(c), _) =>
        addressTypeService.getByCode(c).map {
          case Some(addressType) => {
            val response = ApiSuccess(Json.toJson(addressType),"200:OK", s"Fetch details successfully for id: ${c}")
            Ok(Json.toJson(response))
          }
          case None => {
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for code: ${c}")
            BadRequest(Json.toJson(response))
          }
        }

      case (_, _, Some(n)) =>
        addressTypeService.getByName(n).map {
          case Some(addressType) => {
            val response = ApiSuccess(Json.toJson(addressType),"200:OK", s"Fetch details successfully for id: ${n}")
            Ok(Json.toJson(response))
          }
          case None => {
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for name: ${n}")
            BadRequest(Json.toJson(response))
          }
        }

      case _ =>
        addressTypeService.getList().map(addressTypes => {
          val response = ApiSuccess(Json.toJson(addressTypes),"200:OK", s"Fetch list successfully")
          Ok(Json.toJson(response))
        })
    }
  }
}
