package daos

import models.{DocumentType, DocumentTypeTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class DocumentTypeDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                               (implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  private val documentTypes = DocumentTypeTable.query

  def getAll(): Future[Seq[DocumentType]] = db.run(documentTypes.result)
  def getById(id: Long): Future[Option[DocumentType]] = db.run(documentTypes.filter((_.id === id)).result.headOption)
  def getByCode(code: String): Future[Option[DocumentType]] = db.run(documentTypes.filter((_.code === code)).result.headOption)
  def getByName(name: String): Future[Option[DocumentType]] = db.run(documentTypes.filter((_.name === name)).result.headOption)
  def create(documentType: DocumentType): Future[DocumentType] =
    db.run((documentTypes returning documentTypes.map(_.id))+= documentType).map(id => documentType.copy(id = id))
  def update(id: Long, documentType: DocumentType): Future[Option[DocumentType]] =
    db.run(documentTypes.filter(_.id === id).update(documentType)).map {
      case 0 => None
      case _ => Some(documentType)
    }
  def delete(id: Long): Future[Boolean] =
    db.run(documentTypes.filter(_.id === id).delete.map(_ > 0))
}
