
//Simple Class
class Person(name: String, age: Int) {
  def show() {
    println(name + ""+age)
  }
}
object MainObject {
  def main(args: Array[String]): Unit = {
    var person = new Person("Sanjay", 49)
    person.show()
  }
}
//Class With Primary Constructor
//class Person(name: String, age: Int) {
//  def show(): Unit = {
//    println("name : "+name+", age: "+age)
//  }
//}
//object MainObject {
//  def main(args: Array[String]): Unit = {
//    var person = new Person("Sanjay", 49)
//    var person1 = new Person("Ajay", 40)
//    person.show()
//    person1.show()
//  }
//}

class Maths{
  def add(a: Int, b: Int): Unit = {
    var sum = a+b
    println("Sum: "+sum)
  }
}

object ClassObjectExample {
  def main(args: Array[String]): Unit = {
    new Maths().add(10,20)
  }
}

