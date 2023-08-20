package daos

import models.{AddressType, AddressTypeTable}

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class AddressTypeDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
                              implicit executionContext: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val addressTypes = AddressTypeTable.query

  def getAll(): Future[Seq[AddressType]] = db.run(addressTypes.result)

  def getById(id: Long): Future[Option[AddressType]] =
    db.run(addressTypes.filter(_.id === id).result.headOption)

  def getByCode(code: String): Future[Option[AddressType]] =
    db.run(addressTypes.filter(_.code === code).result.headOption)

  def getByName(name: String): Future[Option[AddressType]] =
    db.run(addressTypes.filter(_.name === name).result.headOption)

  def create(addressType: AddressType): Future[AddressType] =
    db.run((addressTypes returning addressTypes.map(_.id)) += addressType).map(id => addressType.copy(id = id))

  def update(id: Long, addressType: AddressType): Future[Option[AddressType]] =
    db.run(addressTypes.filter(_.id === id).update(addressType)).map {
      case 0 => None
      case _ => Some(addressType)
    }

  def delete(id: Long): Future[Boolean] =
    db.run(addressTypes.filter(_.id === id).delete).map(_ > 0)
}
