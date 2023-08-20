package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.Tag
import slick.model.Table

import scala.concurrent.{ExecutionContext, Future}

case class User(id: Long, name: String)

class UserTable(tag: Tag) extends Table[User] (tag, "users") {
  def id = columns[Long]("id", )
}