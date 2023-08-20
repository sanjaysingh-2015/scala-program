package daos

import models.{User, UserTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class UserDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)
                        (implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val users = UserTable.query

  def create(user: User): Future[User] =
    db.run(users returning users.map(_.id) += user).map(id => user.copy(id = id))

  def update(id: Long, user: User): Future[Option[User]] =
    db.run(users.filter(_.id === id).update(user)).map {
      case 0 => None
      case _ => Some(user)
    }

  def getAll: Future[Seq[User]] = db.run(users.result)

  def getById(id: Long): Future[Option[User]] =
    db.run(users.filter(_.id === id).result.headOption)

  def getByUserName(userName: String): Future[Option[User]] =
    db.run(users.filter(_.userName startsWith userName).result.headOption)

  def getByName(name: String): Future[Seq[User]] =
    db.run(users.filter {
      user =>
        val firstName = user.firstName
        val middleName = user.middleName
        val lastName = user.lastName
        firstName.startsWith(name) || middleName.startsWith(name) || lastName.startsWith(name)
    }.result)

  def getByEmail(email: String): Future[Seq[User]] =
    db.run(users.filter(_.email startsWith email).result)

  def getByPhoneNo(phoneNo: String): Future[Seq[User]] =
    db.run(users.filter{
      user =>
        val mobileNo = user.mobileNo
        val alternateMobileNo = user.alternateMobileNo
        mobileNo.startsWith(phoneNo) || alternateMobileNo.startsWith(phoneNo)
    }.result)

  def delete(id: Long): Future[Boolean]  =
    db.run(users.filter(_.id === id).delete).map(_ > 0)
}
