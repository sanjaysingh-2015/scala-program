package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.{ ExecutionContext, Future }

case class User(id: Long, name: String)

class UserTable(tag: Tag) extends Table[User](tag, "users") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  def * = (id, name) <> (User.tupled, User.unapply)
}

@Singleton
class UserDAO @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val users = TableQuery[UserTable]

  def createUser(user: User): Future[User] =
    db.run {
      (users returning users.map(_.id) into ((user, id) => user.copy(id = id))) += user
    }

  def updateUser(user: User): Future[Int] =
    db.run {
      users.filter(_.id === user.id).update(user)
    }

  def deleteUser(userId: Long): Future[Int] =
    db.run {
      users.filter(_.id === userId).delete
    }

  def listUsers: Future[Seq[User]] =
    db.run {
      users.result
    }
}
