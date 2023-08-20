package controllers

import models.KycDocumentType
import payloads.common.{ApiError, ApiSuccess, StatusUpdatePayload}
import payloads.{KycDocumentTypeRequest, KycDocumentTypeResponse}
import play.api.libs.json._
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.KycDocumentTypeService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KycDocumentTypeController @Inject()(cc: ControllerComponents,
                                          kycDocumentTypeService: KycDocumentTypeService)
                                         (implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {
  implicit val apiSuccessReads: Reads[ApiSuccess] = Json.reads[ApiSuccess]
  implicit val apiErrorReads: Reads[ApiError] = Json.reads[ApiError]
  implicit val statusUpdatePayloadReads: Reads[StatusUpdatePayload] = Json.reads[StatusUpdatePayload]
  implicit val documentTypeReads: Reads[KycDocumentType] = Json.reads[KycDocumentType]
  implicit val documentTypeRequestReads: Reads[KycDocumentTypeRequest] = Json.reads[KycDocumentTypeRequest]
  implicit val documentTypeResponseReads: Reads[KycDocumentTypeResponse] = Json.reads[KycDocumentTypeResponse]
  implicit val apiSuccessWrites: Writes[ApiSuccess] = Json.writes[ApiSuccess]
  implicit val apiErrorWrites: Writes[ApiError] = Json.writes[ApiError]
  implicit val statusUpdatePayloadWrites: Writes[StatusUpdatePayload] = Json.writes[StatusUpdatePayload]
  implicit val documentTypeWrites: Writes[KycDocumentType] = Json.writes[KycDocumentType]
  implicit val documentTypeRequestWrites: Writes[KycDocumentTypeRequest] = Json.writes[KycDocumentTypeRequest]
  implicit val documentTypeResponseWrites: Writes[KycDocumentTypeResponse] = Json.writes[KycDocumentTypeResponse]

  def createDocumentType: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[KycDocumentTypeRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      documentType => kycDocumentTypeService.create(documentType).map(createdDocumentType => {
        val response = ApiSuccess(Json.toJson(createdDocumentType), "201:Created", s"Created details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def updateDocumentType(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[KycDocumentTypeRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      documentType => kycDocumentTypeService.update(id, documentType).map(updatedDocumentType => {
        val response = ApiSuccess(Json.toJson(updatedDocumentType), "201:Created", s"Updated details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def updateStatus(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[StatusUpdatePayload].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      statusUpdatePayload => kycDocumentTypeService.statusUpdate(id, statusUpdatePayload).map(updatedDocumentType => {
        val response = ApiSuccess(Json.toJson(updatedDocumentType), "201:Created", s"Updated details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def deleteDocumentType(id: Long): Action[AnyContent] = Action.async { implicit request =>
    kycDocumentTypeService.delete(id).map(status => {
      val response = ApiSuccess(Json.toJson(status), "201:Created", s"Updated details successfully")
      Created(Json.toJson(response))
    })
  }

  def getByFilter(id: Option[Long], code: Option[String], name: Option[String]): Action[AnyContent] = Action.async { implicit request =>
    (id, code, name) match {
      case (Some(i), _, _) =>
        kycDocumentTypeService.getById(i).map {
          case Some(documentType) => {
            val response = ApiSuccess(Json.toJson(documentType), "200:OK", s"Fetch details successfully for id: ${i}")
            Ok(Json.toJson(response))
          }
          case None => {
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for id: ${i}")
            BadRequest(Json.toJson(response))
          }
        }
      case (_, Some(c), _) =>
        kycDocumentTypeService.getByCode(c).map {
          case Some(documentType) => {
            val response = ApiSuccess(Json.toJson(documentType), "200:OK", s"Fetch details successfully for id: ${c}")
            Ok(Json.toJson(response))
          }
          case None => {
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for id: ${c}")
            BadRequest(Json.toJson(response))
          }
        }
      case (_, _, Some(n)) =>
        kycDocumentTypeService.getByName(n).map {
          case Some(documentType) => {
            val response = ApiSuccess(Json.toJson(documentType), "200:OK", s"Fetch details successfully for id: ${n}")
            Ok(Json.toJson(response))
          }
          case None => {
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for id: ${n}")
            BadRequest(Json.toJson(response))
          }
        }
      case _ =>
        kycDocumentTypeService.getAll.map(documentTypes => {
          val response = ApiSuccess(Json.toJson(documentTypes), "200:OK", s"Fetch list successfully")
          Ok(Json.toJson(response))
        })
    }
  }
}
