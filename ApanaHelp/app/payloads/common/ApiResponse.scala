package payloads.common

import play.api.libs.json.JsValue

import scala.concurrent.Future

trait IApiResponse
case class ApiSuccess(data: JsValue, status: String, message: String) extends IApiResponse
case class ApiError(status: String, message: String) extends IApiResponse