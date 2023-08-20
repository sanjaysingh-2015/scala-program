package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.time.LocalDate

class WorkforceKycDocumentTable(tag: Tag) extends Table[WorkforceKycDocument](tag, Some("apanahelp_schema"), "workforce_kyc_document") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def workforceId = column[Long]("workforce_id")
  def kycDocumentTypeId = column[Long]("kyc_document_type_id")
  def kycDocumentTypeMapId = column[Long]("kyc_document_type_map_id")
  def fileName = column[String]("file_name")
  def uploadedOn = column[LocalDate]("uploaded_on")
  def uploadedBy = column[String]("uploaded_by")
  def verified = column[Boolean]("verified")
  def status = column[String]("status")

  override def * = (id, workforceId, kycDocumentTypeId, kycDocumentTypeMapId, fileName, uploadedOn, uploadedBy, verified,status) <> (WorkforceKycDocument.tupled, WorkforceKycDocument.unapply)
}

object WorkforceKycDocumentTable {
  val query: TableQuery[WorkforceKycDocumentTable] = TableQuery[WorkforceKycDocumentTable]
}
