package ObjectProgram
//Write a Scala program that creates an object MathUtils with a static method factorial
// that calculates the factorial of a given number.

object MathUtils {
  def factorial(num: Int): Int = {
    def factHelper(n: Int, acc: Int): Int =
      if(n<=1) acc
      else factHelper(n-1, n * acc)

    factHelper(num,1)
  }
}
object ProblemThree extends App {
  val num = 5
  val result = MathUtils.factorial(5)
  println(s"Factorial of $num = $result")
}
