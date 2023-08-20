package services

import daos.DocumentTypeDao
import models.DocumentType
import payloads.common.StatusUpdatePayload
import payloads.{DocumentTypeRequest, DocumentTypeResponse}
import utils.AppUtility

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class DocumentTypeService @Inject() (documentTypeDao: DocumentTypeDao, appUtility: AppUtility)
                                    (implicit executionContext: ExecutionContext){

  def create(request: DocumentTypeRequest): Future[DocumentTypeResponse] = {
    val code = "DCT-" + appUtility.generateRandomInt(4)
    val documentType = DocumentType(Option.empty, code, request.name, request.description, "OPEN")
    val result = documentTypeDao.create(documentType)
    result.map(documentType => DocumentTypeResponse(documentType.id.get, documentType.code, documentType.name,documentType.description, documentType.status))
  }

  def update(id: Long, request: DocumentTypeRequest): Future[DocumentTypeResponse] = {
    val res = for {
      updatedRequest <- documentTypeDao.getById(id)
      result <- documentTypeDao.update(id, DocumentType(updatedRequest.get.id, updatedRequest.get.code, request.name, request.description, updatedRequest.get.status))
    } yield result
    res.map(documentType => DocumentTypeResponse(documentType.get.id.get, documentType.get.code, documentType.get.name,documentType.get.description, documentType.get.status))
  }

  def statusUpdate(id: Long, request: StatusUpdatePayload): Future[DocumentTypeResponse] = {
    val res = for {
      updatedRequest <- documentTypeDao.getById(id)
      result <- documentTypeDao.update(id, DocumentType(updatedRequest.get.id, updatedRequest.get.code, updatedRequest.get.name, updatedRequest.get.description, request.status))
    } yield result
    res.map(documentType => DocumentTypeResponse(documentType.get.id.get, documentType.get.code, documentType.get.name, documentType.get.description, documentType.get.status))
  }

  def getAll(): Future[Seq[DocumentTypeResponse]] = {
    val response = documentTypeDao.getAll.map { documentTypes =>
      documentTypes.map { case(documentType) =>
        DocumentTypeResponse(documentType.id.get, documentType.code, documentType.name, documentType.description, documentType.status)
      }
    }
    response
  }

  def getById(id: Long): Future[Option[DocumentTypeResponse]] = {
    val response = documentTypeDao.getById(id).map { documentType =>
      documentType.map { case (documentType) =>
        DocumentTypeResponse(documentType.id.get, documentType.code, documentType.name, documentType.description, documentType.status)
      }
    }
    response
  }

  def getByCode(code: String): Future[Option[DocumentTypeResponse]] = {
    val response = documentTypeDao.getByCode(code).map { documentType =>
      documentType.map { case (documentType) =>
        DocumentTypeResponse(documentType.id.get, documentType.code, documentType.name, documentType.description, documentType.status)
      }
    }
    response
  }

  def getByName(name: String): Future[Option[DocumentTypeResponse]] = {
    val response = documentTypeDao.getByName(name).map { documentType =>
      documentType.map { case (documentType) =>
        DocumentTypeResponse(documentType.id.get, documentType.code, documentType.name, documentType.description, documentType.status)
      }
    }
    response
  }

  def delete(id: Long): Future[Boolean] = documentTypeDao.delete(id)
}
