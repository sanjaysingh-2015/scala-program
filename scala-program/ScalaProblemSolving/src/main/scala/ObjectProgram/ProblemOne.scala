package ObjectProgram

//Write a Scala program that creates a class called Person with properties like name, age and country.
// Implement methods to get and set properties.
class Person {
  private var _name: String = ""
  private var _age: Int = 0
  private var _country: String = ""

  def name: String = _name
  def name_=(newName: String): Unit = _name = newName
  def age: Int = _age
  def age_=(newAge: Int): Unit = _age = newAge
  def country: String = _country
  def country_=(newCountry: String): Unit = _country = newCountry

  override def toString: String = s"Name = $name\nAge = $age\nCountry = $country"
}
object ProblemOne extends App {
  val person = new Person()
  person.name = "Sanjay"
  person.age = 48
  person.country = "India"
  println(person.toString)
}
