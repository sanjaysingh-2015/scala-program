package models

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

case class Actor(id: Long, name: String)

class ActorTable(tag: Tag) extends Table[Actor](tag, Some("movies"), "Actor") {
  def id = column[Long]("actor_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  override def * = (id, name) <> (Actor.tupled, Actor.unapply)
}

object ActorTable {
  val query: TableQuery[ActorTable] = TableQuery[ActorTable]
}
