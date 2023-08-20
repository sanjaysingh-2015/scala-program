//Simple Class and Case class difference
object SimpleAndCaseClassExample {
  class Person(name: String, age: Int) {
    def getName: String = name
    def getAge: Int = age
  }

  case class CasePerson(name: String, age: Int)

  def main(args: Array[String]) {
    val person = new Person("Sanjay", 30)
    val casePerson = CasePerson(name = "Sanjeev", age = 25)
    println("Person Name = " + person.getName) // Accessing elements of case class
    println("Case Person Name = " + casePerson.name)

    // val updatedPerson = person.copy(name="Ranjan") there is no direct method to copy normal class
    val updatedCasePerson = casePerson.copy(name="Ranjan")

    // println("Person Name = " + updatedPerson)
    // Accessing elements of case class
    println("Case Person Name = " + updatedCasePerson.name)

    /* Pattern matching
    def processPerson(p: Person): Unit = p match {
      case Person("John", _) => println("John found!")
      case _ => println("Unknown person")
    }*/

    def processCasePerson(cp: CasePerson): Unit = cp match {
      case CasePerson("Sanjeev", _) => println("Sanjeev found!")
      case _ => println("Unknown case person")
    }

    processCasePerson(casePerson)  // Output: Jane found!
  }
}