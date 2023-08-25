package ObjectProgram
//Write a Scala program that creates a class ContactInfo with properties name, email, and address.
// Create a class Customer that includes a ContactInfo object.
class ContactInfo(var _name: String, var _email: String, var _address: String) {
  def name: String = _name
  def name_=(newName: String): Unit = _name = newName
  def email: String = _email
  def email_=(newEmail: String): Unit = _email = newEmail
  def address: String = _address
  def address_=(newAddress: String): Unit = _address = newAddress
  def display: String = s"Contact Info:\nName: $name\nEmail: $email\nAddress: $address"
}

class Customer(var _id: Int, var _code: String, var _contactInfo: ContactInfo) {
  def id: Int = _id
  def id_=(newId: Int): Unit = _id = newId
  def code: String = _code
  def code_=(newCode: String): Unit = _code = newCode
  def contactInfo: ContactInfo = _contactInfo
  def contactInfo_=(newContactInfo: ContactInfo): Unit = _contactInfo = newContactInfo
  def display: String = s"Customer ==>\nId: $id\nCode: $code\n${contactInfo.display}"
}
object ProblemFourteen extends App {
  val contactInfo: ContactInfo = new ContactInfo("Sanjay", "sanjay.s@email.com", "2015/4B, Vasundhara, Ghaziabad")
  val customer: Customer = new Customer(10, "CUST001", contactInfo)
  println(contactInfo.display)
  println("===========================")
  println(customer.display)
}
