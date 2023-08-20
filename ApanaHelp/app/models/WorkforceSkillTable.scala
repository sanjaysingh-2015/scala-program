package models

import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.time.LocalDate

class WorkforceSkillTable(tag: Tag) extends Table[WorkforceSkill](tag, Some("apanahelp_schema"), "workforce_skill") {
  def id = column[Option[Long]]("id", O.PrimaryKey, O.AutoInc)
  def workforceId = column[Long]("workforce_id")
  def skillId = column[Long]("skillId")
  def status = column[String]("status")

  override def * = (id, workforceId, skillId, status) <> (WorkforceSkill.tupled, WorkforceSkill.unapply)
}

object WorkforceSkillTable {
  val query: TableQuery[WorkforceSkillTable] = TableQuery[WorkforceSkillTable]
}
