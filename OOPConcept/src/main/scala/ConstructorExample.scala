class ConstructorClass(id: Int, name: String) {
  def this(id: Int, name: String, age: Int) {
    this(id, name)
    println("Overloaded: "+id + ", " + name + ", " + age)
  }

  def show(): Unit = {
    println(id + ", " + name)
  }
}

object ConstructorExample {
  def main(args: Array[String]): Unit = {
    val obj = new ConstructorClass(1, "Sanjay")
    val objSec = new ConstructorClass(1, "Sanjay", 40)
    objSec.show()
  }
}