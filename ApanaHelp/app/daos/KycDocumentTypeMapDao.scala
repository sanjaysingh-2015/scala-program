package daos

import models.{KycDocumentTypeMap, KycDocumentTypeMapTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KycDocumentTypeMapDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(
                                      implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  private val kycDocumentTypeMaps = KycDocumentTypeMapTable.query

  def getAll: Future[Seq[KycDocumentTypeMap]] = db.run(kycDocumentTypeMaps.result)
  def getById(id: Long): Future[Option[KycDocumentTypeMap]] =
    db.run(kycDocumentTypeMaps.filter(_.id === id).result.headOption)
  def getByKycDocumentId(kycDocId: Long): Future[Seq[KycDocumentTypeMap]] =
    db.run(kycDocumentTypeMaps.filter(_.kycDocumentTypeId === kycDocId).result)
  def getByDocumentId(docId: Long): Future[Seq[KycDocumentTypeMap]] =
    db.run(kycDocumentTypeMaps.filter(_.documentTypeId === docId).result)
  def create(kycDocumentTypeMap: KycDocumentTypeMap): Future[KycDocumentTypeMap] =
    db.run(kycDocumentTypeMaps returning kycDocumentTypeMaps.map(_.id)+= kycDocumentTypeMap).map(id => kycDocumentTypeMap.copy(id = id))
  def update(id: Long, kycDocumentTypeMap: KycDocumentTypeMap): Future[Option[KycDocumentTypeMap]] =
    db.run(kycDocumentTypeMaps.filter(_.id === id).update(kycDocumentTypeMap)).map {
      case 0 => None
      case _ => Some(kycDocumentTypeMap)
    }
  def delete(id: Long): Future[Boolean] =
    db.run(kycDocumentTypeMaps.filter((_.id === id)).delete).map(_ > 0)
}
