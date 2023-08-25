package partone

import scala.collection.mutable

object FlowControl extends App {
  //1. Write a Scala program to check if a given number is positive, negative, or zero
  def checkNum(num: Int): String =
    num match {
      case n if n < 0 => "Negative"
      case n if n > 0 => "Positive"
      case _ => "Zero"
    }
  println(s"Check Num: ${checkNum(0)}")
  println(s"Check Num: ${checkNum(10)}")
  println(s"Check Num: ${checkNum(-10)}")

  //2.Write a Scala program to check if a given number is even or odd
  def isEven(num: Int): String =
    num match {
      case n if n % 2 == 0 => "Even"
      case _ => "Odd"
    }
  println(s"Even or Odd: ${isEven(21)}")
  println(s"Even or Odd: ${isEven(22)}")

  //3. Write a Scala program to find the factorial of a given number
  def factorial(num: Int): Int = {
    def factHelper(n: Int, acc: Int = 1): Int =
      if(n <= 1) acc
      else factHelper(n -1, acc * n)

    factHelper(num, 1)
  }

  val num = 10
  println(s"Factorial of $num: ${factorial(num)}")

  //4. Write a Scala program to print the Fibonacci series up to a given number
  def fibonacci(num: Int): List[Int] = {
    def fiboHelper(n: Int, a: Int, b: Int, acc: List[Int]) : List[Int] = {
      if(n == 0) acc.reverse
      else fiboHelper(n-1, b, a + b, a :: acc)
    }
    if(num <= 0) List.empty
    else fiboHelper(num, 0, 1, List.empty)
  }

  println(s"Fibonacci upto $num -> ${fibonacci(10).mkString(", ")}")

  //5.Write a Scala program to print the multiplication table of a given number
  def multiplicationTable(num: Int): mutable.SortedMap[String, Int] = {
    def tableHelp(num:Int, count: Int, acc: mutable.SortedMap[String, Int]): mutable.SortedMap[String, Int] =
      if(count == 0) acc
      else tableHelp(num, count-1, acc + {s"$count * $num" -> num * count})

    tableHelp(num, 10, mutable.SortedMap.empty)
  }

  println(s"multiplication table of $num :\n${multiplicationTable(num).mkString("\n")}")

  //6.Write a Scala program to find the sum of all elements in an array
  def sumAll(arr: Array[Int]): Int =
    arr.reduce(_ + _)

  val arr: Array[Int] = Array(2,3,4,1,5,9)
  println(s"Sum of all Elements: ${sumAll(arr)}")

  //7.Write a Scala program to check if a given string is a palindrome using pattern matching.
  def isPalindrome(str: String): Boolean =
    str match {
      case s if s == s.reverse => true
      case _ => false
    }

  val str = "level"
  println(s"$str is isPalindrome: ${isPalindrome(str)}")

  //8.Write a Scala program to count the number of vowels in a given string using pattern matching.
  def countVowels(str: String): Int = {
    val vowels = "aeiouAEIOU"

    def countHelper(s: String, acc: Int): Int = {
      s.toList match {
        case Nil => acc
        case head +: tail if vowels.contains(head) => countHelper(tail.mkString, acc + 1)
        case _ +: tail => countHelper(tail.mkString, acc)
      }
    }

    countHelper(str, 0)
  }

  println(s"No of Vowels in '$str' = ${countVowels(str)}")

  //9.Write a Scala program to find the largest element in an array using pattern matching
  def largestNumber(arr: Array[Int]): Int = {
    def largestHelper(arr: Array[Int], largest: Int): Int = {
      arr.toList match {
        case head :: tail if head > largest => largestHelper(tail.toArray, head)
        case _ :: tail => largestHelper(tail.toArray, largest)
        case Nil => largest
      }
    }
    largestHelper(arr, 0)
  }

  println(s"Largest of the List : ${largestNumber(arr)}")
}
