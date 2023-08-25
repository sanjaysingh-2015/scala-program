package ObjectProgram
//Write a Scala program that creates an abstract class Shape with an abstract method area.
// Implement subclasses Rectangle and Circle that override the area method.
abstract class Shape {
  def area(): Double
}

class Rectangle(length: Double, width: Double) extends Shape {
  override def area(): Double = length * width
}

class Circle(radius: Double) extends Shape {
  override def area(): Double = (22/7)*radius*radius
}
object ProblemFour extends App {
  val rectangle = new Rectangle(10,3.0)
  var area = rectangle.area()
  println(s"Area of Rectangle is: $area")
  val circle = new Circle(7.12)
  area = circle.area()
  println(s"Area of Circle is: $area")
}
