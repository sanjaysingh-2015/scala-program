package models

import java.time.LocalDate

case class Movie(movieId: Option[Long], name: String, releaseDate: LocalDate, lengthInMin: Int)
case class Actor(actorId: Option[Long], name: String)
case class MovieActorMapping(movieActorId: Option[Long], movieId: Long, actorId: Long)
