package controllers

import models.DocumentType
import payloads.common.{ApiError, ApiSuccess, StatusUpdatePayload}
import payloads.{DocumentTypeRequest, DocumentTypeResponse}
import play.api.libs.json.{JsError, JsValue, Json, Reads, Writes}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.DocumentTypeService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class DocumentTypeController @Inject()(cc: ControllerComponents,
                                       documentTypeService: DocumentTypeService)
                                      (implicit executionContext: ExecutionContext)
extends AbstractController(cc) {
  implicit val apiSuccessReads: Reads[ApiSuccess] = Json.reads[ApiSuccess]
  implicit val apiErrorReads: Reads[ApiError] = Json.reads[ApiError]
  implicit val statusUpdatePayloadReads: Reads[StatusUpdatePayload] = Json.reads[StatusUpdatePayload]
  implicit val documentTypeReads: Reads[DocumentType] = Json.reads[DocumentType]
  implicit val documentTypeRequestReads: Reads[DocumentTypeRequest] = Json.reads[DocumentTypeRequest]
  implicit val documentTypeResponseReads: Reads[DocumentTypeResponse] = Json.reads[DocumentTypeResponse]
  implicit val apiSuccessWrites: Writes[ApiSuccess] = Json.writes[ApiSuccess]
  implicit val apiErrorWrites: Writes[ApiError] = Json.writes[ApiError]
  implicit val statusUpdatePayloadWrites: Writes[StatusUpdatePayload] = Json.writes[StatusUpdatePayload]
  implicit val documentTypeWrites: Writes[DocumentType] = Json.writes[DocumentType]
  implicit val documentTypeRequestWrites: Writes[DocumentTypeRequest] = Json.writes[DocumentTypeRequest]
  implicit val documentTypeResponseWrites: Writes[DocumentTypeResponse] = Json.writes[DocumentTypeResponse]

  def createDocumentType: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[DocumentTypeRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      documentType => documentTypeService.create(documentType).map(createdDocumentType => {
        val response = ApiSuccess(Json.toJson(createdDocumentType), "201:Created", s"Created details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def updateDocumentType(id: Long): Action[JsValue] = Action.async(parse.json) { implicit  request =>
    request.body.validate[DocumentTypeRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      documentType => documentTypeService.update(id, documentType).map(updatedDocumentType => {
        val response = ApiSuccess(Json.toJson(updatedDocumentType), "201:Created", s"Updated details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def updateStatus(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[StatusUpdatePayload].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      statusPayload => documentTypeService.statusUpdate(id, statusPayload).map(updatedDocumentType => {
        val response = ApiSuccess(Json.toJson(updatedDocumentType), "201:Created", s"Updated details successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def deleteDocumentType(id: Long): Action[AnyContent] = Action.async {implicit request =>
    documentTypeService.delete(id).map(status => {
      val response = ApiSuccess(Json.toJson(status), "201:Created", s"Updated details successfully")
      Created(Json.toJson(response))
    })
  }

  def getByFilter(id: Option[Long], code: Option[String], name: Option[String]): Action[AnyContent] = Action.async { implicit  request =>
    (id, code, name) match {
      case (Some(i), _, _) =>
        documentTypeService.getById(i).map {
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
        documentTypeService.getByCode(c).map {
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
        documentTypeService.getByName(n).map {
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
        documentTypeService.getAll.map(documentTypes => {
            val response = ApiSuccess(Json.toJson(documentTypes), "200:OK", s"Fetch list successfully")
            Ok(Json.toJson(response))
        })
    }
  }
}
