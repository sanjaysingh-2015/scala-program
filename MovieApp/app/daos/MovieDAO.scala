package daos

import com.google.inject.Inject
import models.{Actor, ActorTable, Movie, MovieActorMapping, MovieActorMappingTable, MovieTable}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class MovieDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(
  implicit executionContext: ExecutionContext
) extends HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  private val movies = TableQuery[MovieTable]
  private val actors = TableQuery[ActorTable]
  private val movieActorMappings = TableQuery[MovieActorMappingTable]

  def addMovieWithActors(movie: Movie, actorNames: Seq[String]): Future[Movie] = {
    val action = for {
      insertedMovieId <- (movies returning movies.map(_.movieId)) += movie
      existingActors <- actors.filter(_.name.inSet(actorNames)).result
      newActorNames = actorNames.diff(existingActors.map(_.name))
      insertedActors <- actors ++= newActorNames.map(name => Actor(Option.empty, name = name))
      insertedMappings <- movieActorMappings ++= insertedActors.map(actor => MovieActorMapping(Option.empty, insertedMovieId, actor))
    } yield insertedMovieId

    db.run(action.transactionally)
      .map(insertedMovieId => movie.copy(movieId = insertedMovieId))
  }

  def getActorsByMovieId(movieId: Long): Future[Seq[Actor]] = {
    val query = for {
      mapping <- movieActorMappings if mapping.movieId === movieId
      actor <- actors.filter(_.id === mapping.actorId)
    } yield actor

    db.run(query.result)
  }
}

