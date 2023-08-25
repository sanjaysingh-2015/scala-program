package partone

object FunctionProgram extends App {
  //1.Write a Scala function to check if a given number is prime.
  def isPrime(num: Int): Boolean = {
    def primeHelper(no: Int, div: Int): Boolean = {
      if(div * div > no) true
      else if(no % div == 0) false
      else primeHelper(no, div + 1)
    }
    if(num <= 1) false
    else primeHelper(num, 2)
  }
  val num1 = 17
  val num2 = 18
  println(s"$num1 is ${isPrime(num1)}")
  println(s"$num2 is ${isPrime(num2)}")

  //2.Write a Scala function to calculate the sum of digits in a given number.
  def sumDigit(num: Int): Int = {
    def sumHelper(n: Int, acc: Int): Int = {
      if(n < 10) acc
      else sumHelper(n / 10, acc + (n % 10))
    }
    sumHelper(num, 0)
  }

  val num3 = 129938
  println(s"Sum of Digit $num3 is ${sumDigit(num3)}")

  //3.Write a Scala function to reverse a given string
  def reverseString(str: String): String = {
    def reverseHelper(s: List[Char], acc: List[Char]): List[Char] = {
      s match {
        case Nil => acc
        case head :: tail => reverseHelper(tail, head :: acc)
      }
    }
    reverseHelper(str.toList, List.empty).mkString
  }

  println(reverseString("Sanjay"))

  //4.Write a Scala function to check if a given string is a palindrome
  def isPalindrome(str: String): Boolean = {
    str match {
      case s if s == reverseString(s) => true
      case _ => false
    }
  }

  val str = "level"
  println(s"$str is palindrome? ${isPalindrome(str)}")
  //5.Write a Scala function to check if a given number is a perfect square
  def perfectSquare(num: Int): Boolean = {
    Math.sqrt(num) match {
      case sq if sq.toInt * sq.toInt == num => true
      case _ => false
    }
  }

  val val1 = 25
  val val2 = 30
  println(s"$val1 is perfect square? ${perfectSquare(val1)}")
  println(s"$val2 is perfect square? ${perfectSquare(val2)}")

  //6.Write a Scala function to check if a given list is sorted in ascending order.
  def checkOrder(list: List[Int]): Boolean =
    list match {
      case Nil => true
      case _ :: Nil => true
      case head :: subHead :: tail if head <= subHead => checkOrder(tail)
      case _ => false
    }

  val arr = List(1,3,5,7,8,9)
  println(checkOrder(arr))
}
