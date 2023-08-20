package payloads

import java.time.LocalDate

case class UserRequest(userName: String,firstName: String,middleName: String,
                       lastName: String, email: String, mobileNo: String,alternateMobileNo: String)
case class UserResponse(id: Long,userName: String,firstName: String,middleName: String,
                       lastName: String, email: String, mobileNo: String,alternateMobileNo: String,
                       createdOn: LocalDate, createdBy: String, status: String)
