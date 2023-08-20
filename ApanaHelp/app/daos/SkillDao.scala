package daos

import models.{Skill, SkillTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class SkillDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                         (implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val skills = SkillTable.query

  def create(skill: Skill): Future[Skill] =
    db.run((skills returning skills.map(_.id)) += skill).map(id => skill.copy(id = id))

  def update(id: Long, skill: Skill): Future[Option[Skill]] =
    db.run(skills.filter(_.id === id).update(skill)).map {
      case 0 => None
      case _ => Some(skill)
    }

  def getAll: Future[Seq[Skill]] =
    db.run(skills.result)

  def getById(id: Long): Future[Option[Skill]] =
    db.run(skills.filter(_.id === id).result.headOption)

  def getByCode(code: String): Future[Option[Skill]] =
    db.run(skills.filter(_.code === code).result.headOption)

  def getByName(name: String): Future[Option[Skill]] =
    db.run(skills.filter(_.name === name).result.headOption)

  def delete(id: Long): Future[Boolean] =
    db.run(skills.filter(_.id === id).delete).map(_ > 0)
}
