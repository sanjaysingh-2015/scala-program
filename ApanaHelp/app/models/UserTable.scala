package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.time.LocalDate

class UserTable(tag: Tag) extends Table[User](tag, Some("apanahelp_schema"), "user") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def userName = column[String]("user_name")
  def firstName = column[String]("first_name")
  def middleName = column[String]("middle_name")
  def lastName = column[String]("last_name")
  def email = column[String]("email")
  def mobileNo = column[String]("mobile_no")
  def alternateMobileNo = column[String]("alternate_mobile_no")
  def createdOn = column[LocalDate]("created_on")
  def createdBy = column[String]("created_by")
  def status = column[String]("status")

  override def * = (id, userName, firstName, middleName, lastName, email, mobileNo, alternateMobileNo, createdOn, createdBy, status) <> (User.tupled, User.unapply)
}

object UserTable {
  val query: TableQuery[UserTable] = TableQuery[UserTable]
}
