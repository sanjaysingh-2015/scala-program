package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.{ProvenShape, Tag}

class KycDocumentTypeMapTable (tag: Tag) extends Table[KycDocumentTypeMap](tag, Some("apanahelp_schema"), "kyc_document_type_map"){
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def kycDocumentTypeId = column[Long]("kyc_document_type_id")
  def documentTypeId = column[Long]("document_type_id")
  def status = column[String]("status")
  override def * : ProvenShape[KycDocumentTypeMap] = (id, kycDocumentTypeId, documentTypeId, status) <> (KycDocumentTypeMap.tupled, KycDocumentTypeMap.unapply)
}

object KycDocumentTypeMapTable {
  val query: TableQuery[KycDocumentTypeMapTable] = TableQuery[KycDocumentTypeMapTable]
}