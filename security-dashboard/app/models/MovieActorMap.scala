package models

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

case class MovieActorMap(id: Long, movieId: Long, actorId: Long)

class MovieActorMapTable(tag: Tag) extends Table[MovieActorMap](tag: Tag, Some("movies"), "MovieActorMapping") {
  def id = column[Long]("movie_actor_id", O.PrimaryKey, O.AutoInc)
  def movieId = column[Long]("movie_id")
  def actorId = column[Long]("actor_id")

  override def * : ProvenShape[MovieActorMap] = (id, movieId, actorId) <> (MovieActorMap.tupled, MovieActorMap.unapply)
}

object MovieActorMapTable {
  val query: TableQuery[MovieActorMapTable] = TableQuery[MovieActorMapTable]
}