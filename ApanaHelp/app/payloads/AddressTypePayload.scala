package payloads

case class AddressTypeRequest(name: String)
case class AddressTypeResponse(id: Long, code: String, name: String)
