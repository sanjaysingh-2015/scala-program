package controllers

import daos.MovieDAO
import models.Movie
import payloads.{ActorRequest, ActorResponse, MovieRequest, MovieResponse}
import play.api.libs.json.{JsValue, Json, Reads, Writes}
import play.api.mvc.{AbstractController, Action, ControllerComponents}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class MovieController @Inject()(cc: ControllerComponents, movieDAO: MovieDAO)(
  implicit executionContext: ExecutionContext
) extends AbstractController(cc) {

  implicit val actorDtoReads: Reads[ActorRequest] = Json.reads[ActorRequest]
  implicit val actorDtoWrites: Writes[ActorResponse] = Json.writes[ActorResponse]
  implicit val movieDetailReads: Reads[MovieRequest] = Json.reads[MovieRequest]
  implicit val movieDetailWrites: Writes[MovieResponse] = Json.writes[MovieResponse]

  def createMovieWithActors(): Action[JsValue] = Action.async(parse.json) { request =>
    val movieResult = request.body.validate[MovieRequest]
    movieResult.fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> "Invalid request format"))),
      movieRequest => {
        val movie = Movie(0, movieRequest.name, movieRequest.releaseDate, movieRequest.lengthInMin)
        val actorNames = movieRequest.actors.map(_.name)

        // Insert the movie and actors, then fetch the inserted actors
        movieDAO.addMovieWithActors(movie, actorNames).flatMap { insertedMovie =>
          movieDAO.getActorsByMovieId(insertedMovie.movieId).map { insertedActors =>
            val responseActors = insertedActors.map(actor => ActorResponse(actor.actorId, actor.name))
            val responseMovie = MovieResponse(
              insertedMovie.movieId,
              insertedMovie.name,
              insertedMovie.releaseDate,
              insertedMovie.lengthInMin,
              responseActors
            )
            Ok(Json.toJson(responseMovie))
          }
        }
      }
    )
  }
}

