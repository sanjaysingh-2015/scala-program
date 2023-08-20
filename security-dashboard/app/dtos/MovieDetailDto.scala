package dtos

import java.time.LocalDate

case class ActorDto(name: String)
case class MovieDto(name: String, releaseDate: LocalDate, lengthInMin: Int)
case class MovieDetailDto(name: String, releaseDate: LocalDate, lengthInMin: Int, actors: List[ActorDto])

