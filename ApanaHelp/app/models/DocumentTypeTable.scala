package models

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

class DocumentTypeTable (tag: Tag) extends Table[DocumentType](tag, Some("apanahelp_schema"), "document_type") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def code = column[String]("code")
  def name = column[String]("name")
  def description = column[String]("description")
  def status = column[String]("status")

  override def * : ProvenShape[DocumentType] = (id, code, name, description, status) <>(DocumentType.tupled, DocumentType.unapply)
}

object DocumentTypeTable {
  val query: TableQuery[DocumentTypeTable] = TableQuery[DocumentTypeTable]
}