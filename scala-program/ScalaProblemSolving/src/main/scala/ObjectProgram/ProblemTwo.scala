package ObjectProgram

//Write a Scala program that creates a subclass Student that extends the Person class.
// Add a property called grade and implement methods to get and set it.
class Student extends Person {
  private var _grade: String = ""

  def grade: String = _grade
  def grade_=(newGrade: String): Unit = _grade = newGrade
  override def toString: String = s"${super.toString}\nGrade = $grade"
}
object ProblemTwo extends App {
  val student = new Student()
  student.name = "Sanjay"
  student.age = 48
  student.country = "India"
  student.grade = "XII"
  println(student.toString)
}
