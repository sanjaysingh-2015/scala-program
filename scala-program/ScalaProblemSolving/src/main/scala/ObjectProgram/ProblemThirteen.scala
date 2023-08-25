package ObjectProgram
//Write a Scala program that creates an enum class Color with values for different colors.
// Use the enum class to represent an object's color.

object Color extends Enumeration {
  type Color = Value
  val Red, Blue, Green, Orange, White, Black, Yellow = Value
}

class ObjectWithColor(val color: Color.Color)
object ProblemThirteen extends App {
  val red = new ObjectWithColor(Color.Red)
  val white = new ObjectWithColor(Color.White)

  println(s"Red object's color: ${red.color}")
  println(s"White object's color: ${white.color}")
}
