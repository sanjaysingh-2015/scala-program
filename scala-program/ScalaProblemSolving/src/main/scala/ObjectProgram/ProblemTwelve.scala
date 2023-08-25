package ObjectProgram

case class Point(x: Double, y: Double)
// Write a Scala program that creates a class Point with properties x and y coordinates.
// Use a destructuring declaration to extract the coordinates
object ProblemTwelve extends App {
  val point = Point(1.2, 3.4)

  val Point(x, y) = point //destructuring declaration
  println(s"Co-ordinate X: $x")
  println(s"Co-ordinate Y: $y")
}
