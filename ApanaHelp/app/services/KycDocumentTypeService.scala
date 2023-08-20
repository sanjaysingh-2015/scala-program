package services

import daos.KycDocumentTypeDao
import models.KycDocumentType
import payloads.common.StatusUpdatePayload
import payloads.{KycDocumentTypeRequest, KycDocumentTypeResponse}
import utils.AppUtility

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KycDocumentTypeService @Inject()(kycDocumentTypeDao: KycDocumentTypeDao, appUtility: AppUtility)
                                      (implicit executionContext: ExecutionContext) {

  def create(request: KycDocumentTypeRequest): Future[KycDocumentTypeResponse] = {
    val code = "DCT-" + appUtility.generateRandomInt(4)
    val kycDocumentType = KycDocumentType(Option.empty, code, request.name, "OPEN")
    val result = kycDocumentTypeDao.create(kycDocumentType)
    result.map(documentType => KycDocumentTypeResponse(documentType.id.get, documentType.code, documentType.name, documentType.status))
  }

  def update(id: Long, request: KycDocumentTypeRequest): Future[KycDocumentTypeResponse] = {
    val res = for {
      updatedRequest <- kycDocumentTypeDao.getById(id)
      result <- kycDocumentTypeDao.update(id, KycDocumentType(updatedRequest.get.id, updatedRequest.get.code, request.name, updatedRequest.get.status))
    } yield result
    res.map(kycDocumentType => KycDocumentTypeResponse(kycDocumentType.get.id.get, kycDocumentType.get.code, kycDocumentType.get.name, kycDocumentType.get.status))
  }

  def statusUpdate(id: Long, request: StatusUpdatePayload): Future[KycDocumentTypeResponse] = {
    val res = for {
      updatedRequest <- kycDocumentTypeDao.getById(id)
      result <- kycDocumentTypeDao.update(id, KycDocumentType(updatedRequest.get.id, updatedRequest.get.code, updatedRequest.get.name, request.status))
    } yield result
    res.map(kycDocumentType => KycDocumentTypeResponse(kycDocumentType.get.id.get, kycDocumentType.get.code, kycDocumentType.get.name, kycDocumentType.get.status))
  }

  def getAll(): Future[Seq[KycDocumentTypeResponse]] = {
    val response = kycDocumentTypeDao.getAll.map { documentTypes =>
      documentTypes.map { case (kycDocumentType) =>
        KycDocumentTypeResponse(kycDocumentType.id.get, kycDocumentType.code, kycDocumentType.name, kycDocumentType.status)
      }
    }
    response
  }

  def getById(id: Long): Future[Option[KycDocumentTypeResponse]] = {
    val response = kycDocumentTypeDao.getById(id).map { kycDocumentType =>
      kycDocumentType.map { case (kycDocumentType) =>
        KycDocumentTypeResponse(kycDocumentType.id.get, kycDocumentType.code, kycDocumentType.name, kycDocumentType.status)
      }
    }
    response
  }

  def getByCode(code: String): Future[Option[KycDocumentTypeResponse]] = {
    val response = kycDocumentTypeDao.getByCode(code).map { kycDocumentType =>
      kycDocumentType.map { case (kycDocumentType) =>
        KycDocumentTypeResponse(kycDocumentType.id.get, kycDocumentType.code, kycDocumentType.name, kycDocumentType.status)
      }
    }
    response
  }

  def getByName(name: String): Future[Option[KycDocumentTypeResponse]] = {
    val response = kycDocumentTypeDao.getByName(name).map { kycDocumentType =>
      kycDocumentType.map { case (kycDocumentType) =>
        KycDocumentTypeResponse(kycDocumentType.id.get, kycDocumentType.code, kycDocumentType.name, kycDocumentType.status)
      }
    }
    response
  }

  def delete(id: Long): Future[Boolean] = kycDocumentTypeDao.delete(id)
}
