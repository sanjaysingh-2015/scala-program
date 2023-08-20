package daos

import dtos.ActorDto

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import java.time.LocalDate
import models.{Actor, ActorTable, Movie, MovieActorMapTable, MovieTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

class MovieDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(
  implicit executionContext: ExecutionContext
) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val movies = MovieTable.query
  private val actors = ActorTable.query
  private val movieActorMap = MovieActorMapTable.query

  def getMovieById(id: Long): Future[Option[(Movie, Seq[Actor])]] = {
    val query = for {
      (movie, map) <- movies.filter(_.id === id) joinLeft movieActorMap on (_.id === _.movieId)
      actor <- actors.filter(_.id === map.map(_.actorId))
    } yield (movie, actor)

    db.run(query.result).map { rows =>
      rows.headOption.map { case (movie, actor) =>
        (movie, rows.collect { case (_, a) => a })
      }
    }
  }

  def getAllMovies: Future[Seq[(Movie, Seq[Actor])]] = {
    val query = for {
      (movie, map) <- movies joinLeft movieActorMap on (_.id === _.movieId)
      actor <- actors.filter(_.id === map.map(_.actorId))
    } yield (movie, actor)

    db.run(query.result).map { rows =>
      rows.groupBy(_._1).view.mapValues(_.collect { case (_, a) => a }).toSeq
    }
  }

  def getAll(): Future[Seq[Movie]] = {
    db.run(movies.result)
  }

  def getById(id: Long): Future[Option[Movie]] =
    db.run(movies.filter(_.id === id).result.headOption)

  def create(name: String, releaseDate: LocalDate, lengthInMin: Int): Future[Movie] = {
    val movie = Movie(0, name, releaseDate, lengthInMin)
    db.run((movies returning movies.map(_.id)) += movie).map(id => movie.copy(id = id))
  }

  def update(id: Long, name: String, releaseDate: LocalDate, lengthInMin: Int, actors: List[ActorDto]): Future[Option[Movie]] = {
    val movieToUpdate = Movie(id, name, releaseDate, lengthInMin)
    db.run(movies.filter(_.id === id).update(movieToUpdate)).map {
      case 0 => None
      case _ => Some(movieToUpdate)
    }
  }

  def delete(id: Long): Future[Boolean] =
    db.run(movies.filter(_.id === id).delete).map(_ > 0)
}