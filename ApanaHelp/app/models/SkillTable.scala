package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

class SkillTable(tag: Tag) extends Table[Skill](tag, Some("apanahelp_schema"), "skills") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def code = column[String]("code")
  def name = column[String]("name")

  override def * = (id, code, name) <> (Skill.tupled, Skill.unapply)
}

object SkillTable {
  val query: TableQuery[SkillTable] = TableQuery[SkillTable]
}
