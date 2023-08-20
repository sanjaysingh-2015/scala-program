package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.time.LocalDate

class WorkforceTable(tag: Tag) extends Table[Workforce](tag, Some("apanahelp_schema"), "workforce") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("first_name")
  def middleName = column[String]("middle_name")
  def lastName = column[String]("last_name")
  def email = column[String]("email")
  def mobileNo = column[String]("mobile_no")
  def alternateMobileNo = column[String]("alternate_mobile_no")
  def dateOfBirth = column[LocalDate]("date_of_birth")
  def gender = column[String]("gender")
  def verified = column[Boolean]("verified")
  def createdOn = column[LocalDate]("created_on")
  def createdBy = column[String]("created_by")
  def verifiedBy = column[String]("verified_by")
  def verifiedOn = column[LocalDate]("verified_on")
  def status = column[String]("status")
  def workforceCode = column[String]("workforce_code")

  override def * = (id, firstName, middleName, lastName, email, mobileNo, alternateMobileNo, dateOfBirth, gender, verified, createdOn, createdBy, verifiedBy, verifiedOn, status, workforceCode) <> (Workforce.tupled, Workforce.unapply)
}

object WorkforceTable {
  val query: TableQuery[WorkforceTable] = TableQuery[WorkforceTable]
}
