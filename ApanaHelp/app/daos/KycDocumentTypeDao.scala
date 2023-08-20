package daos

import models.{KycDocumentType, KycDocumentTypeTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class KycDocumentTypeDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                  (implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  private val kycDocumentTypes = KycDocumentTypeTable.query

  def getAll(): Future[Seq[KycDocumentType]] = db.run(kycDocumentTypes.result)
  def getById(id: Long): Future[Option[KycDocumentType]] = db.run(kycDocumentTypes.filter((_.id === id)).result.headOption)
  def getByCode(code: String): Future[Option[KycDocumentType]] = db.run(kycDocumentTypes.filter((_.code === code)).result.headOption)
  def getByName(name: String): Future[Option[KycDocumentType]] = db.run(kycDocumentTypes.filter((_.name === name)).result.headOption)
  def create(kycDocumentType: KycDocumentType): Future[KycDocumentType] =
    db.run((kycDocumentTypes returning kycDocumentTypes.map(_.id))+= kycDocumentType).map(id => kycDocumentType.copy(id = id))
  def update(id: Long, kycDocumentType: KycDocumentType): Future[Option[KycDocumentType]] =
    db.run(kycDocumentTypes.filter(_.id === id).update(kycDocumentType)).map {
      case 0 => None
      case _ => Some(kycDocumentType)
    }
  def delete(id: Long): Future[Boolean] =
    db.run(kycDocumentTypes.filter(_.id === id).delete.map(_ > 0))
}
