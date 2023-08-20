package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.time.LocalDate

class WorkforceAddressTable(tag: Tag) extends Table[WorkforceAddress](tag, Some("apanahelp_schema"), "workforce_address") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def workforceId = column[Long]("workforce_id")
  def addressTypeId = column[Long]("address_type_id")
  def stayFrom = column[LocalDate]("stay_from")
  def stayTo = column[LocalDate]("stay_to")
  def addressLine1 = column[String]("address_line1")
  def addressLine2 = column[String]("address_line2")
  def city = column[String]("city")
  def state = column[String]("state")
  def country = column[String]("country")
  def zipcode = column[String]("zipcode")
  def status = column[String]("status")

  override def * = (id, workforceId, addressTypeId, stayFrom, stayTo, addressLine1, addressLine2, city, state, country,zipcode,status) <> (WorkforceAddress.tupled, WorkforceAddress.unapply)
}

object WorkforceAddressTable {
  val query: TableQuery[WorkforceAddressTable] = TableQuery[WorkforceAddressTable]
}
