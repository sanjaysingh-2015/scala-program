package daos

import models.{MovieActorMap, MovieActorMapTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class MovieActorMapDao @Inject() (protected val dbConfigProvider : DatabaseConfigProvider)(
                                 implicit executionContext: ExecutionContext)
extends HasDatabaseConfigProvider[JdbcProfile]{
  import profile.api._

  private val movieActorMaps = MovieActorMapTable.query

  def getAll(): Future[Seq[MovieActorMap]] = db.run(movieActorMaps.result)

  def getById(id: Long): Future[Option[MovieActorMap]] = db.run((movieActorMaps.filter(_.id === id).result.headOption))

  def getByMovieId(movieId: Long): Future[Seq[MovieActorMap]] = db.run(movieActorMaps.filter((_.movieId === movieId)).result)

  def getByMovieIdAndActorId(movieId: Long, actorId: Long): Future[Option[MovieActorMap]] = db.run(movieActorMaps.filter((_.movieId === movieId)).filter(_.actorId === actorId).result.headOption)

  def getByActorId(actorId: Long): Future[Seq[MovieActorMap]] = db.run(movieActorMaps.filter((_.actorId === actorId)).result)

  def createMovieActorMap(movieId: Long, actorId: Long): Future[MovieActorMap] = {
    val movieActorMap = MovieActorMap(0L, movieId, actorId)
    db.run(movieActorMaps returning movieActorMaps.map(_.id) += movieActorMap).map(id => movieActorMap.copy(id = id))
  }

  def updateMoveActorMap(id: Long, movieId: Long, actorId: Long): Future[Option[MovieActorMap]] = {
    val mapToUpdate =  MovieActorMap(id, movieId, actorId)
    db.run(movieActorMaps.filter(_.id === id).update(mapToUpdate)).map {
      case 0 => None
      case _ => Some(mapToUpdate)
    }
  }

  def delete(id: Long): Future[Boolean] =
    db.run(movieActorMaps.filter(_.id === id).delete).map(_ > 0)

  def deleteByMovieId(movieId: Long): Future[Boolean] =
    db.run(movieActorMaps.filter(_.movieId === movieId).delete).map(_ > 0)
}
