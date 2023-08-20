package repositories

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.{AsyncResultSet, BoundStatement, PreparedStatement}
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import com.datastax.oss.driver.api.querybuilder.QueryBuilder.bindMarker
import models.User

import javax.inject.Inject
import scala.compat.java8.FutureConverters.CompletionStageOps
import scala.concurrent.{ExecutionContext, Future, Promise}
import scala.util.{Failure, Success, Try}

class UserRepository @Inject()(session: CqlSession)(implicit ec: ExecutionContext) {
  private val keyspace = "persons"
  private val table = "users"
  
  def createUser(user: User): Future[Try[Unit]] = {
    val insertStatement = QueryBuilder
      .insertInto(keyspace, table)
      .value("id", bindMarker())
      .value("name", bindMarker())
      .value("email", bindMarker())
      .build()

    val preparedInsert = session.prepare(insertStatement)

    val boundInsert = preparedInsert.bind()
      .setUuid(0, java.util.UUID.randomUUID()) // Assuming UUID as id
      .setString(1, user.name)
      .setString(2, user.email)

    val insertResult: Future[AsyncResultSet] = session.executeAsync(boundInsert).toScala
    insertResult.map(_ => Success(()))
      .recover { case exception => Failure(exception) }
  }

  def getUser(id: String): Future[Option[Unit]] = {
    val selectStatement = QueryBuilder
      .selectFrom(keyspace, table)
      .all()
      .whereColumn("id")
      .isEqualTo(bindMarker())
      .build()

    val preparedSelect = session.prepare(selectStatement)

    val boundSelect = preparedSelect.bind()
      .setUuid(0, java.util.UUID.fromString(id))

    val getResult: Future[AsyncResultSet] = session.executeAsync(boundSelect).whenComplete { (resultSet, throwable) =>
      Option(resultSet.one()).map { row =>
        val name = row.getString("name")
        val email = row.getString("email")
        User(id, name, email)
      }
    }.toScala

    getResult.transform {
      case Success(_) => Success(Option(User))
      case Failure(exception) => Failure(exception)
    }
  }

//  def updateUser(user: User): Future[User] = {
//    val updateStatement = QueryBuilder
//      .update(keyspace, table)
//      .setColumn("name", bindMarker())
//      .setColumn("email", bindMarker())
//      .whereColumn("id")
//      .isEqualTo(bindMarker())
//      .build()
//
//    val preparedUpdate = session.prepare(updateStatement)
//
//    val boundUpdate = preparedUpdate.bind()
//      .setString(0, user.name)
//      .setString(1, user.email)
//      .setUuid(2, java.util.UUID.fromString(user.id))
//
//    val updateResult: Future[AsyncResultSet] = session.executeAsync(boundUpdate).toScala
//
//    updateResult.transform {
//      case Success(_) => Success(user)
//      case Failure(exception) => Failure(exception)
//    }
//  }
//
//  def deleteUser(id: String): Future[Unit] = {
//    val deleteStatement = QueryBuilder
//      .deleteFrom(keyspace, table)
//      .whereColumn("id")
//      .isEqualTo(bindMarker())
//      .build()
//
//    val preparedDelete = session.prepare(deleteStatement)
//
//    val boundDelete = preparedDelete.bind()
//      .setUuid(0, java.util.UUID.fromString(id))
//
//    val deleteResult = session.executeAsync(boundDelete).toScala
//
//    deleteResult.transform {
//      case Success(_) => Success(())
//      case Failure(exception) => Failure(exception)
//    }
//  }
}