package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import play.api.libs.json.{JsError, JsValue, Json, Reads, Writes}

import scala.concurrent.{ExecutionContext, Future}
import models.{Actor, Movie, MovieActorMap}
import daos.{ActorDao, MovieActorMapDao, MovieDAO}
import dtos.{ActorDto, ActorResponseDto, MovieDetailDto, MovieResponseDto}

class MovieController @Inject()(
                                 cc: ControllerComponents,
                                 movieDAO: MovieDAO,
                                 movieActorMapDao: MovieActorMapDao,
                                 actorDao: ActorDao
                               )(implicit executionContext: ExecutionContext)
  extends AbstractController(cc) {

  implicit val movieReads: Reads[Movie] = Json.reads[Movie]
  implicit val movieWrites: Writes[Movie] = Json.writes[Movie]
  implicit val actorDtoReads: Reads[ActorDto] = Json.reads[ActorDto]
  implicit val actorDtoWrites: Writes[ActorDto] = Json.writes[ActorDto]
  implicit val movieDetailReads: Reads[MovieDetailDto] = Json.reads[MovieDetailDto]
  implicit val movieDetailWrites: Writes[MovieDetailDto] = Json.writes[MovieDetailDto]

  implicit val actorResponseReads: Reads[ActorResponseDto] = Json.reads[ActorResponseDto]
  implicit val actorResponseWrites: Writes[ActorResponseDto] = Json.writes[ActorResponseDto]
  implicit val movieResponseReads: Reads[MovieResponseDto] = Json.reads[MovieResponseDto]
  implicit val movieResponseWrites: Writes[MovieResponseDto] = Json.writes[MovieResponseDto]

  def createMovie: Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[MovieDetailDto].fold(
      errors => Future.successful(BadRequest(Json.obj("message" -> JsError.toJson(errors)))),
      movie => {
        val createMovieFuture = for {
          createdMovie <- movieDAO.create(movie.name, movie.releaseDate, movie.lengthInMin)
          _ <- Future.traverse(movie.actors) { actorDto =>
            actorDao.getByName(actorDto.name).flatMap {
              case Some(actor) =>
                movieActorMapDao.createMovieActorMap(createdMovie.id, actor.id)
              case None => {
                actorDao.createActor(actorDto.name).map(actor => {
                  movieActorMapDao.createMovieActorMap(createdMovie.id, actor.id)
                })
              }
            }
          }
        } yield createdMovie

        createMovieFuture.flatMap { createdMovie =>
          getResponse(createdMovie.id, createdMovie).map { responseMovie =>
            Created(Json.toJson(responseMovie))
          }
        }
      }
    )
  }

  def updateMovie(id: Long): Action[JsValue] = Action.async(parse.json) { implicit request =>
    request.body.validate[MovieDetailDto].fold(
      errors => Future.successful(BadRequest("Invalid data")),
      movie => {
        val createMovieFuture = for {
          _ <- movieActorMapDao.deleteByMovieId(id)
          updatedMovie <- movieDAO.update(id, movie.name, movie.releaseDate, movie.lengthInMin, movie.actors)
          _ <- Future.traverse(movie.actors) { actorDto =>
            actorDao.getByName(actorDto.name).flatMap {
              case Some(actor) =>
                movieActorMapDao.createMovieActorMap(updatedMovie.get.id, actor.id)
              case None => {
                actorDao.createActor(actorDto.name).map(actor => {
                  movieActorMapDao.createMovieActorMap(updatedMovie.get.id, actor.id)
                })
              }
            }
          }
        } yield updatedMovie

        createMovieFuture.flatMap { updatedMovie =>
          getResponse(updatedMovie.get.id, updatedMovie.get).map { responseMovie =>
            Created(Json.toJson(responseMovie))
          }
        }
      }
    )
  }

  def getAllMovies: Action[AnyContent] = Action.async { implicit request =>
    movieDAO.getAllMovies.map { movieWithActors =>
      val response = movieWithActors.map { case (movie, actors) =>
        Json.obj(
          "id" -> movie.id,
          "name" -> movie.name,
          "releaseDate" -> movie.releaseDate.toString,
          "lengthInMin" -> movie.lengthInMin,
          "actors" -> actors.map(a => Json.obj("id" -> a.id, "name" -> a.name))
        )
      }
      Ok(Json.toJson(response))
    }
  }

  def getMovieById(id: Long): Action[AnyContent] = Action.async { implicit request =>
    movieDAO.getMovieById(id).map {
      case Some((movie, actors)) =>
        val response = Json.obj(
          "id" -> movie.id,
          "name" -> movie.name,
          "releaseDate" -> movie.releaseDate.toString,
          "lengthInMin" -> movie.lengthInMin,
          "actors" -> actors.map(a => Json.obj("id" -> a.id, "name" -> a.name))
        )
        Ok(response)
      case None => NotFound
    }
  }

  def deleteMovie(id: Long): Action[AnyContent] = Action.async { implicit request =>
    movieDAO.delete(id).map {
      case true => NoContent
      case false => NotFound
    }
  }

  def getResponse(id: Long, updatedMovie: Movie): Future[MovieResponseDto] = {
    actorDao.getActorsByMovieId(id).map { insertedActors =>
      val responseActors = insertedActors.map(actor => ActorResponseDto(actor.id, actor.name))
      val responseMovie = MovieResponseDto(
        updatedMovie.id,
        updatedMovie.name,
        updatedMovie.releaseDate,
        updatedMovie.lengthInMin,
        responseActors
      )
      responseMovie
    }
  }
}
