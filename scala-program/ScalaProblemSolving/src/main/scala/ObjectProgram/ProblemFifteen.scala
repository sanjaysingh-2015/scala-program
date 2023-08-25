package ObjectProgram
//Write a Scala program that creates a class Shape
// with a nested class Dimensions to store the dimensions of a shape.
class MyShape {
  class Dimensions(height: Double, width: Double) {
    override def toString: String = s"Height: $height, Width: $width"
  }
}
object ProblemFifteen extends App {
  val shape = new MyShape()
  val dimension = new shape.Dimensions(3.1, 4)
  println(dimension)
}
