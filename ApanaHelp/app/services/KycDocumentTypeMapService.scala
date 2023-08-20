package services

import daos.{DocumentTypeDao, KycDocumentTypeDao, KycDocumentTypeMapDao}
import models.KycDocumentTypeMap
import payloads.common.StatusUpdatePayload
import payloads.{KycDocumentTypeMapRequest, KycDocumentTypeMapResponse}
import utils.AppUtility

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KycDocumentTypeMapService @Inject() (kycDocumentTypeMapDao: KycDocumentTypeMapDao,
                                           kycDocumentTypeDao: KycDocumentTypeDao,
                                           documentTypeDao: DocumentTypeDao)
                                          (implicit val executionContext: ExecutionContext){
  def create(kycDocumentTypeMapRequest: KycDocumentTypeMapRequest): Future[KycDocumentTypeMapResponse] = {
    val kycDocumentTypeMap = KycDocumentTypeMap(Option.empty,kycDocumentTypeMapRequest.kycDocumentTypeId, kycDocumentTypeMapRequest.documentTypeId,"OPEN")
    val result = kycDocumentTypeMapDao.create(kycDocumentTypeMap)

    result.flatMap(res => {
      kycDocumentTypeDao.getById(res.kycDocumentTypeId).flatMap( kycDoc => {
        documentTypeDao.getById(res.documentTypeId).map(doc => {
          KycDocumentTypeMapResponse(res.id.get,kycDoc.get.id.get,kycDoc.get.code,kycDoc.get.name, doc.get.id.get, doc.get.code, doc.get.name, res.status)
        })
      })
    })
  }

  def update(id: Long, kycDocumentTypeMapRequest: KycDocumentTypeMapRequest): Future[KycDocumentTypeMapResponse] = {
    val res = for {
      updatedResult <- kycDocumentTypeMapDao.getById(id)
      result <- kycDocumentTypeMapDao.update(id, KycDocumentTypeMap(updatedResult.get.id, kycDocumentTypeMapRequest.kycDocumentTypeId, kycDocumentTypeMapRequest.documentTypeId, updatedResult.get.status))
    } yield result
    res.flatMap(res => {
      kycDocumentTypeDao.getById(res.get.kycDocumentTypeId).flatMap(kycDoc => {
        documentTypeDao.getById(res.get.documentTypeId).map(doc => {
          KycDocumentTypeMapResponse(res.get.id.get, kycDoc.get.id.get, kycDoc.get.code, kycDoc.get.name, doc.get.id.get, doc.get.code, doc.get.name, res.get.status)
        })
      })
    })
  }

  def statusUpdate(id: Long, request: StatusUpdatePayload): Future[KycDocumentTypeMapResponse] = {
    val res = for {
      updatedResult <- kycDocumentTypeMapDao.getById(id)
      result <- kycDocumentTypeMapDao.update(id, KycDocumentTypeMap(updatedResult.get.id, updatedResult.get.kycDocumentTypeId, updatedResult.get.documentTypeId, request.status))
    } yield result
    res.flatMap(res => {
      kycDocumentTypeDao.getById(res.get.kycDocumentTypeId).flatMap(kycDoc => {
        documentTypeDao.getById(res.get.documentTypeId).map(doc => {
          KycDocumentTypeMapResponse(res.get.id.get, kycDoc.get.id.get, kycDoc.get.code, kycDoc.get.name, doc.get.id.get, doc.get.code, doc.get.name, res.get.status)
        })
      })
    })
  }

  def delete(id: Long): Future[Boolean] = kycDocumentTypeMapDao.delete(id)

  def getAll: Future[Seq[KycDocumentTypeMapResponse]] = {
    kycDocumentTypeMapDao.getAll.flatMap(results => {
      Future.sequence {
        results.map(res => {
          kycDocumentTypeDao.getById(res.kycDocumentTypeId).flatMap(kycDoc => {
            documentTypeDao.getById(res.documentTypeId).map(doc => {
              KycDocumentTypeMapResponse(res.id.get, kycDoc.get.id.get, kycDoc.get.code, kycDoc.get.name, doc.get.id.get, doc.get.code, doc.get.name, res.status)
            })
          })
        })
      }
    })
  }

  def getById(id: Long): Future[Option[KycDocumentTypeMapResponse]] = {
    kycDocumentTypeMapDao.getById(id).flatMap {
      case Some(res) =>
        kycDocumentTypeDao.getById(res.kycDocumentTypeId).flatMap { kycDoc =>
          documentTypeDao.getById(res.documentTypeId).map { doc =>
            Some(
              KycDocumentTypeMapResponse(res.id.get, kycDoc.get.id.get, kycDoc.get.code, kycDoc.get.name, doc.get.id.get, doc.get.code, doc.get.name, res.status)
            )
          }
        }
      case None => Future.successful(None)
    }
  }

  def getByKycDocumentId(kycDocId: Long): Future[Seq[KycDocumentTypeMapResponse]] = {
    kycDocumentTypeMapDao.getByKycDocumentId(kycDocId).flatMap(results => {
      Future.sequence {
        results.map(res => {
          kycDocumentTypeDao.getById(res.kycDocumentTypeId).flatMap(kycDoc => {
            documentTypeDao.getById(res.documentTypeId).map(doc => {
              KycDocumentTypeMapResponse(res.id.get, kycDoc.get.id.get, kycDoc.get.code, kycDoc.get.name, doc.get.id.get, doc.get.code, doc.get.name, res.status)
            })
          })
        })
      }
    })
  }

  def getByDocumentId(docId: Long): Future[Seq[KycDocumentTypeMapResponse]] = {
    kycDocumentTypeMapDao.getByDocumentId(docId).flatMap(results => {
      Future.sequence {
        results.map(res => {
          kycDocumentTypeDao.getById(res.kycDocumentTypeId).flatMap(kycDoc => {
            documentTypeDao.getById(res.documentTypeId).map(doc => {
              KycDocumentTypeMapResponse(res.id.get, kycDoc.get.id.get, kycDoc.get.code, kycDoc.get.name, doc.get.id.get, doc.get.code, doc.get.name, res.status)
            })
          })
        })
      }
    })
  }
}
