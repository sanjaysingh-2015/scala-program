package models

import slick.lifted.{ProvenShape, Tag}
import slick.jdbc.PostgresProfile.api._

import java.time.LocalDate

class MovieTable(tag: Tag) extends Table[Movie](tag, Some("movies"), "Movie") {
  // columns
  def movieId = column[Long]("movie_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def releaseDate = column[LocalDate]("release_date")
  def lengthInMin = column[Int]("length_in_min")

  // projection
  def * = (movieId, name, releaseDate, lengthInMin) <> (Movie.tupled, Movie.unapply)
}

class ActorTable(tag: Tag) extends Table[Actor](tag, Some("movies"), "Actor") {
  def id = column[Long]("actor_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")

  override def * = (id, name) <> (Actor.tupled, Actor.unapply)
}

class MovieActorMappingTable(tag: Tag) extends Table[MovieActorMapping](tag: Tag, Some("movies"), "MovieActorMapping") {
  def id = column[Long]("movie_actor_id", O.PrimaryKey, O.AutoInc)
  def movieId = column[Long]("movie_id")
  def actorId = column[Long]("actor_id")

  override def * : ProvenShape[MovieActorMapping] = (id, movieId, actorId) <> (MovieActorMapping.tupled, MovieActorMapping.unapply)
}
