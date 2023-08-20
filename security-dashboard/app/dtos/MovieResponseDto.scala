package dtos

import java.time.LocalDate

case class ActorResponseDto(id: Long, name: String)
case class MovieResponseDto(id: Long, name: String, releaseDate: LocalDate, lengthInMin: Int, actors: Seq[ActorResponseDto])

