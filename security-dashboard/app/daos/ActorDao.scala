package daos

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import models.{Actor, ActorTable, MovieActorMapTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

class ActorDao @Inject() (protected val dbConfigProvider: DatabaseConfigProvider,
                          movieActorMapDao: MovieActorMapDao)(
                         implicit executionContext: ExecutionContext
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val actors = ActorTable.query
  private val movieActorMappings = MovieActorMapTable.query

  def getAll(): Future[Seq[Actor]] = db.run(actors.result)

  def getById(id: Long): Future[Option[Actor]] = db.run(actors.filter(_.id === id).result.headOption)
  def getByName(name: String): Future[Option[Actor]] = db.run(actors.filter(_.name === name).result.headOption)

  def getActorsByMovieId(movieId: Long): Future[Seq[Actor]] = {
    val query = for {
      mapping <- movieActorMappings if mapping.movieId === movieId
      actor <- actors if actor.id === mapping.actorId
    } yield actor

    db.run(query.result)
  }

  def createActor(name: String): Future[Actor] = {
    val actor = new Actor(0L, name)
    db.run((actors returning actors.map(_.id)) += actor).map(id => actor.copy(id = id))
  }

  def updateActor(id: Long, name: String): Future[Option[Actor]] = {
    val actorToUpdate = Actor(id, name)
    db.run(actors.filter(_.id === id).update(actorToUpdate)).map {
      case 0 => None
      case _ => Some(actorToUpdate)
    }
  }

  def delete(id: Long) : Future[Boolean] =
    db.run(actors.filter(_.id === id).delete).map(_ > 0)
}
