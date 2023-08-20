package models

import java.time.LocalDate
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.sql.Date

case class Movie(id: Long, name: String, releaseDate: LocalDate, lengthInMin: Int)

class MovieTable(tag: Tag) extends Table[Movie](tag, Some("movies"),"Movie") {
  def id = column[Long]("movie_id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def releaseDate = column[LocalDate]("release_date")
  def lengthInMin = column[Int]("length_in_min")

  // Custom formatter for LocalDate
  implicit val localDateColumnType: BaseColumnType[LocalDate] = MappedColumnType.base[LocalDate, Date](
    ld => Date.valueOf(ld),
    d => d.toLocalDate
  )
  override def * = (id, name, releaseDate, lengthInMin) <> (Movie.tupled, Movie.unapply)

}

object MovieTable {
  val query: TableQuery[MovieTable] = TableQuery[MovieTable]
}

