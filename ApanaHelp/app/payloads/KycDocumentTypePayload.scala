package payloads

case class KycDocumentTypeRequest(name: String)
case class KycDocumentTypeResponse(id: Long, code: String, name: String, status: String)
