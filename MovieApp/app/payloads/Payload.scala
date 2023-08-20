package payloads

import java.time.LocalDate

case class ActorRequest(name: String)
case class MovieRequest(name: String, releaseDate: LocalDate, lengthInMin: Int, actors: Seq[ActorRequest])
case class ActorResponse(id: Long, name: String)
case class MovieResponse(id: Long, name: String, releaseDate: LocalDate, lengthInMin: Int, actors: Seq[ActorResponse])

