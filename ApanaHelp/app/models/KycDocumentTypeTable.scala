package models

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

class KycDocumentTypeTable (tag: Tag) extends Table[KycDocumentType](tag, Some("apanahelp_schema"), "kyc_document_type"){
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def code = column[String]("code")
  def name = column[String]("name")
  def status = column[String]("status")
  override def * : ProvenShape[KycDocumentType] = (id, code, name, status) <> (KycDocumentType.tupled, KycDocumentType.unapply)
}

object KycDocumentTypeTable {
  val query: TableQuery[KycDocumentTypeTable] = TableQuery[KycDocumentTypeTable]
}