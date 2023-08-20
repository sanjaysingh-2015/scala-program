package payloads

case class SkillRequest(name: String)
case class SkillResponse(id: Long, code: String, name: String)
