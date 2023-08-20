abstract class Shape {
  def area(): Double     // Abstract method

  def display(): Unit = {   // Concrete method
    println("This is a shape.")
  }
}

class Rectangle(width: Double, height: Double) extends Shape {
  override def area(): Double = width * height
}

class Circle(radius: Double) extends Shape {
  override def area(): Double = math.Pi * radius * radius
}

object AbstractClassExample {
  def main(args:Array[String]): Unit = {
    val rectangle = new Rectangle(5.0, 3.0)
    println(rectangle.area()) // Output: 15.0
    rectangle.display() // Output: This is a shape.

    val circle = new Circle(2.5)
    println(circle.area()) // Output: 19.634954084936208
    circle.display() // Output: This is a shape.
  }
}
