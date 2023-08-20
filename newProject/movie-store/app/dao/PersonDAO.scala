package dao

import javax.inject.Inject
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.{Document, MongoCollection}
import utils.MongoClientProvider
import scala.concurrent.{ExecutionContext, Future}

case class Person(_id: ObjectId, name: String, age: Int)

class PersonDAO @Inject()(mongoClientProvider: MongoClientProvider)(implicit ec: ExecutionContext) {
  private val collection: MongoCollection[Person] =
    mongoClientProvider.database.getCollection[Person]("persons")

  def getAll: Future[Seq[Person]] = {
    collection.find().toFuture()
  }

  def getById(id: ObjectId): Future[Option[Person]] = {
    collection.find(equal("_id", id)).headOption()
  }

  def create(person: Person): Future[Unit] = {
    collection.insertOne(person).toFuture().map(_ => ())
  }

//  def update(id: ObjectId, name: String, age: Int): Future[Boolean] = {
//    collection.updateOne(equal("_id", id), set("name", name) ++ set("age", age))
//      .toFuture()
//      .map()
//  }

  def delete(id: ObjectId): Future[Boolean] = {
    collection.deleteOne(equal("_id", id)).toFuture().map(_.getDeletedCount > 0)
  }
}
