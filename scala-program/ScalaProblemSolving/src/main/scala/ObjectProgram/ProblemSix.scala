package ObjectProgram
//Write a Scala program that creates a class Employee with properties like name, age, and designation.
// Implement a method to display employee details.

class Employee(val name: String, val age: Int, val designation: String) {
  def display: String = s"Employee Details:\nName = $name\nAge = $age\nDesignation = $designation"
}
object ProblemSix extends App {
  val employee = new Employee("Sanjay", 48, "Tech Lead")
  println(employee.display)
}
