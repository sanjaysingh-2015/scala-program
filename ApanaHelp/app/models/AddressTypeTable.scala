package models

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

class AddressTypeTable(tag: Tag) extends Table[AddressType](tag, Some("apanahelp_schema"), "address_type") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def code = column[String]("code")
  def name = column[String]("name")

  override def * = (id, code, name) <> (AddressType.tupled, AddressType.unapply)
}

object AddressTypeTable {
  val query: TableQuery[AddressTypeTable] = TableQuery[AddressTypeTable]
}
