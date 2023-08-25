package ObjectProgram
//Write a Scala program that creates a class Triangle with properties side1, side2, and side3.
// Implement a method isEquilateral to check if the triangle is equilateral.

class Triangle(val side1: Double, val side2: Double, val side3: Double) {
  def isEquilateral: Boolean =
    (side1, side2, side3) match {
      case (s1, s2, s3) if s1 == s2 && s1 == s3 => true
      case _ => false
    }
}

object ProblemNine extends App {
  val triangle= new Triangle(3.12, 3.13, 3.12)
  val result = triangle.isEquilateral
  println(s"Triangle is Equilateral $result")
}
