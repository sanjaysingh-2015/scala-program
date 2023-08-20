package payloads

case class KycDocumentTypeMapRequest(kycDocumentTypeId: Long, documentTypeId: Long)
case class KycDocumentTypeMapResponse(id: Long, kycDocumentTypeId: Long, kycDocumentTypeCode: String, kycDocumentTypeName: String, documentTypeId: Long, documentTypeCode: String, documentTypeName: String, status: String)
