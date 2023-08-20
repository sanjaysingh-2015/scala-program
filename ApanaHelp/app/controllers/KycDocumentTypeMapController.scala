package controllers

import models.{KycDocumentType, KycDocumentTypeMap}
import payloads.{KycDocumentTypeMapRequest, KycDocumentTypeMapResponse}
import payloads.common.{ApiError, ApiSuccess, StatusUpdatePayload}
import play.api.libs.json.{JsError, JsValue, Json, Reads, Writes}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.KycDocumentTypeMapService
import utils.CsvProcessingService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KycDocumentTypeMapController @Inject()(cc: ControllerComponents,
                                             kycDocumentTypeMapService: KycDocumentTypeMapService,
                                             csvProcessingService: CsvProcessingService)
                                            (implicit val executionContext: ExecutionContext)
extends AbstractController(cc){
  implicit val apiSuccessReads: Reads[ApiSuccess] = Json.reads[ApiSuccess]
  implicit val apiErrorReads: Reads[ApiError] = Json.reads[ApiError]
  implicit val statusUpdatePayloadReads: Reads[StatusUpdatePayload] = Json.reads[StatusUpdatePayload]
  implicit val documentTypeReads: Reads[KycDocumentTypeMap] = Json.reads[KycDocumentTypeMap]
  implicit val documentTypeRequestReads: Reads[KycDocumentTypeMapRequest] = Json.reads[KycDocumentTypeMapRequest]
  implicit val documentTypeResponseReads: Reads[KycDocumentTypeMapResponse] = Json.reads[KycDocumentTypeMapResponse]
  implicit val apiSuccessWrites: Writes[ApiSuccess] = Json.writes[ApiSuccess]
  implicit val apiErrorWrites: Writes[ApiError] = Json.writes[ApiError]
  implicit val statusUpdatePayloadWrites: Writes[StatusUpdatePayload] = Json.writes[StatusUpdatePayload]
  implicit val documentTypeWrites: Writes[KycDocumentTypeMap] = Json.writes[KycDocumentTypeMap]
  implicit val documentTypeRequestWrites: Writes[KycDocumentTypeMapRequest] = Json.writes[KycDocumentTypeMapRequest]
  implicit val documentTypeResponseWrites: Writes[KycDocumentTypeMapResponse] = Json.writes[KycDocumentTypeMapResponse]

  def create: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[KycDocumentTypeMapRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      kycDocumentTypeMap => kycDocumentTypeMapService.create(kycDocumentTypeMap).map(createdMap => {
        val response = ApiSuccess(Json.toJson(createdMap), "201:Created", s"Created map successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def update(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[KycDocumentTypeMapRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      kycDocumentTypeMap => kycDocumentTypeMapService.update(id, kycDocumentTypeMap).map(updatedMap => {
        val response = ApiSuccess(Json.toJson(updatedMap), "201:Created", s"Updated map successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def updateStatus(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[StatusUpdatePayload].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      kycDocumentTypeMap => kycDocumentTypeMapService.statusUpdate(id, kycDocumentTypeMap).map(updatedMap => {
        val response = ApiSuccess(Json.toJson(updatedMap), "201:Created", s"Updated map successfully")
        Created(Json.toJson(response))
      })
    )
  }

  def delete(id: Long): Action[AnyContent] = Action.async { implicit request =>
    kycDocumentTypeMapService.delete(id).map(status => {
      val response = ApiSuccess(Json.toJson(status), "201:Created", s"Deleted map successfully")
      Created(Json.toJson(response))
    })
  }

  def getByFilter(id: Option[Long], kycDocId: Option[Long], docId: Option[Long]): Action[AnyContent] = Action.async { implicit request =>
    (id, kycDocId, docId) match {
      case ( Some(i), _, _) =>
        kycDocumentTypeMapService.getById(i).map {
          case Some(documentType) => {
            val response = ApiSuccess(Json.toJson(documentType), "200:OK", s"Fetch details successfully for id: ${i}")
            Ok(Json.toJson(response))
          }
          case None => {
            val response = ApiError("501:BadRequest", s"Unable to Fetch details for id: ${i}")
            BadRequest(Json.toJson(response))
          }
        }
      case (_, Some(kdi), _) =>
        kycDocumentTypeMapService.getByKycDocumentId(kdi).map(documentTypes => {
          val response = ApiSuccess(Json.toJson(documentTypes), "200:OK", s"Fetch list successfully")
          Ok(Json.toJson(response))
        })
      case (_, _, Some(di)) =>
        kycDocumentTypeMapService.getByKycDocumentId(di).map(documentTypes => {
          val response = ApiSuccess(Json.toJson(documentTypes), "200:OK", s"Fetch list successfully")
          Ok(Json.toJson(response))
        })
      case _ =>
        kycDocumentTypeMapService.getAll.map(documentTypes => {
          val response = ApiSuccess(Json.toJson(documentTypes), "200:OK", s"Fetch list successfully")
          Ok(Json.toJson(response))
        })
    }
  }

  def uploadCSV = Action.async(parse.multipartFormData) { request =>
    request.body.file("file").map { filePart =>
      val uploadedFile = filePart.ref.path.toFile
      csvProcessingService.processCSV(uploadedFile).map { _ =>
        val response = ApiSuccess(Json.toJson(""), "200:OK", s"Uploaded successfully successfully")
        Ok(Json.toJson(response))
      }
    }.getOrElse {
      val response = ApiSuccess(Json.toJson(""), "400:Failed", s"Upload failed")
      Future.successful(BadRequest(Json.toJson(response)))
    }
  }
}
