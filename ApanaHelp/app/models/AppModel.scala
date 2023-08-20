package models

import java.time.LocalDate

case class AddressType(id: Option[Long], code: String, name: String)
case class DocumentType(id: Option[Long], code: String, name: String, description: String, status: String)
case class KycDocumentType(id: Option[Long], code: String, name: String, status: String)
case class KycDocumentTypeMap(id: Option[Long], kycDocumentTypeId: Long, documentTypeId: Long, status: String)
case class Skill(id: Option[Long], code: String, name: String)
case class User(id: Option[Long],userName: String,firstName: String,middleName: String,
                lastName: String, email: String, mobileNo: String,alternateMobileNo: String,
                createdOn: LocalDate, createdBy: String, status: String)
case class Workforce(id: Option[Long],firstName: String,middleName: String,
                     lastName: String, email: String, mobileNo: String,alternateMobileNo: String,
                     dateOfBirth: LocalDate,gender: String,verified: Boolean, createdOn: LocalDate,
                     createdBy: String, verifiedBy: String, verifiedOn: LocalDate, status: String,
                     workforceCode: String)
case class WorkforceAddress(id: Option[Long],workforceId: Long,addressTypeId:Long,stayFrom:LocalDate,
                            stayTo:LocalDate, addressLine1:String,addressLine2:String,
                            city:String,state:String,country:String,zipcode:String,status:String)
case class WorkforceKycDocument(id: Option[Long],workforceId: Long,kycDocumentTypeId: Long,
                                kycDocumentTypeMapId: Long,fileName:String,uploadedOn:LocalDate,
                                uploadedBy:String,verified: Boolean,status:String)
case class WorkforceSkill(id: Option[Long], workforceId: Long, skillId: Long, status: String)
