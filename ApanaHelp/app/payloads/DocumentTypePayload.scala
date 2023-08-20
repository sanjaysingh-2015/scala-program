package payloads

case class DocumentTypeRequest(name: String, description: String)
case class DocumentTypeResponse(id: Long, code: String, name: String, description: String, status: String)
