package models

import play.api.libs.json.{Json, OFormat}

case class User(id: String, name: String, email: String)

object User {
  implicit val format: OFormat[User] = Json.format[User]
}
