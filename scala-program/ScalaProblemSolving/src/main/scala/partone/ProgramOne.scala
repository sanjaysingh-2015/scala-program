package partone

object ProgramOne extends App {
  //1.Write a Scala program to compute the sum of the two given integer values.
  //  If the two values are the same, then return triples their sum.
  def sumNumber(num1: Int, num2: Int): Int =
    (num1, num2) match {
      case (n1, n2) if(n1 == n2) => n1 * 3
      case (n1, n2) => n1 + n2
    }

  val num1 = 10
  val num2 = 10
  println(s"Sum of $num1 and $num2 is ${sumNumber(num1, num2)}")

  //2.Write a Scala program to get the absolute difference between n and 51.
  // If n is greater than 51 return triple the absolute difference
  def difference51(num: Int): Int =
    num match {
      case n if n > 51 => Math.abs(n - 51) * 3
      case _ => Math.abs(n - 51)
    }

  val n = 40
  println(s"Absolute difference of $n and 51 is ${difference51(n)}")

  //2.Write a Scala program to check two given integers,
  // and return true if one of them is 30 or if their sum is 30.
  def check30(num1: Int, num2: Int): Boolean =
    (num1, num2) match {
      case (30, _) => true
      case (_, 30) => true
      case (n1, n2) if(n1 + n2 == 30) => true
      case _ => false
    }

  val no1 = 10
  val no2 = 10
  println(s"Check if 30: ${check30(no1,no2)}")

  //4. Write a Scala program to check a given integer and
  // return true if it is within 20 of 100 or 300.
  def checkRange(num: Int): Boolean =
    num match {
      case n if (n >= 20 && n <= 100) || n == 300 => true
      case _ => false
    }

  val num3 = 99
  println(s"Check range: ${checkRange(num3)}")

  //5.Write a Scala program to create a new string where 'if' is added to the front of a given string.
  // If the string already begins with 'if', return the string unchanged
  def stringStartWithIf(str: String): String =
    str match {
      case s if s.startsWith("If") => s
      case s => s"If $s"
    }

  val str = "Scala, Hello world!"
  println(s"String starts with If: ${stringStartWithIf(str)}")

  //6.Write a Scala program to remove the character in a given position of a given string.
  // The given position will be in the range 0...string length -1 inclusive.
  def removeChar(str: String, pos: Int): String =
    str match {
      case str if pos < str.length => str.substring(0, pos-1) + str.substring(pos, str.length)
      case _ => "Invalid position"
    }

  val str1 = "The Scala World"
  val pos = 10
  println(s"Remove character at $pos from $str1: ${removeChar(str1, pos)}")

  //7.Write a Scala program to exchange the first and last characters
  // in a given string and return the new string.
  def exchangeChar(str: String): String =
    str.charAt(str.length - 1) + str.substring(1, str.length-1) + str.charAt(0)

  val str2 = "The World of Scala"
  println(exchangeChar(str2))

  //8.Write a Scala program to create a new string which is 4 copies of the 2 front characters of a given string.
  // If the given string length is less than 2 return the original string.
  def fourCopies(str: String): String =
    str match {
      case s if s.length < 2 => s
      case s => s.substring(0,2) * 4
    }

  val str3 = "Scala"
  println(fourCopies(str3))

  //9.Write a Scala program to create a new string with the last char added at
  // the front and back of a given string of length 1 or more.
  def padChar(str: String): String = {
    if (str.length < 1) str
    val lastChar = str.charAt(str.length - 1)
    s"$lastChar$str$lastChar"
  }
  println(padChar(str2))

  //10.Write a Scala program to check whether a given positive number is a
  // multiple of 3 or a multiple of 7.

  def multipleOf3Or7(num: Int): Boolean =
    num match {
      case n if n < 0 => false
      case n if n % 3 == 0 || n % 7 == 0 => true
      case _ => false
    }

  println(multipleOf3Or7(-21))

  //11.Write a Scala program to create a new string taking
  // the first 3 characters of a given string and return the string with
  // the 3 characters added at both the front and back.
  // If the given string length is less than 3, use whatever characters are there.
  def threeCharPadding(str: String): String =
    str match {
      case str if str.length < 3 => str * 3
      case str => str.substring(0,3)+str+str.substring(0,3)
    }

  println(threeCharPadding(str2))

  //12.Write a Scala program to check whether a given string starts with 'Sc' or not
  def startWithSc(str: String): Boolean =
    str match {
      case s if s.startsWith("Sc") => true
      case _ => false
    }

  println(startWithSc(str2))
  println(startWithSc(str3))

  //13.Write a Scala program to check whether one of the given temperatures is
  // less than 0 and the other is greater than 100.
  def checkTemp(temp1: Int, temp2: Int): Boolean =
    (temp1, temp2) match {
      case (t1, t2) if((t1 < 0 && t2 > 100) || (t2 < 0 && t1 > 100)) => true
      case _ => false
    }

  println(s"Check Temp: ${checkTemp(-10, 110)}")
  println(s"Check Temp: ${checkTemp(110, -10)}")
  println(s"Check Temp: ${checkTemp(110, 10)}")
  println(s"Check Temp: ${checkTemp(10, 110)}")
  println(s"Check Temp: ${checkTemp(10, 90)}")

  //14.Write a Scala program to check two given integers whether either of them is in the range 100..200 inclusive.
  def checkNumRange(num1: Int, num2: Int): Boolean =
    (num1, num2) match  {
    case (n1, n2) if((n1 >= 100 && n1 <= 200) || (n2 >= 100 && n2 <= 200)) => true
    case _ => false
  }

  println(s"Check Num Range: ${checkNumRange(100, 10)}")
  println(s"Check Num Range: ${checkNumRange(10, 100)}")
  println(s"Check Num Range: ${checkNumRange(10, 10)}")

  //15. Write a Scala program to check whether three given integer values are in
  // the range 20..50 inclusive.
  // Return true if 1 or more of them are in the said range otherwise false
  def checkThreeNumRange(num1: Int, num2: Int, num3: Int): Boolean =
    (num1, num2, num3) match {
      case (n1, n2, n3)
        if((n1 >= 20 && n1 <= 50) || (n2 >= 20 && n2 <= 50) || (n3 >= 20 && n3 <= 50)) => true
      case _ => false
    }

  println(s"Check Three Num Range: ${checkThreeNumRange(20, 30, 40)}")
  println(s"Check Three Num Range: ${checkThreeNumRange(10, 20, 30)}")
  println(s"Check Three Num Range: ${checkThreeNumRange(10, 15, 30)}")
  println(s"Check Three Num Range: ${checkThreeNumRange(10, 15, 60)}")

  //16.Write a Scala program to check whether a string 'yt' appears at index 1 in a given string.
  // If it appears return a string without 'yt' otherwise return the original string.
  def startWithYt(str: String): String =
    str match {
      case s if s.startsWith("yt") => s.replace("yt","")
      case s => s
    }

  println(s"Start with YT: ${startWithYt(str2)}")
  val ytStr = "yt Test String"
  println(s"Start with YT: ${startWithYt(ytStr)}")

  //17.Write a Scala program to check the largest number among three given integers.
  def largestOfThree(num1: Int, num2: Int, num3: Int): Int =
    (num1, num2, num3) match {
      case (n1, n2, n3) if(n1 > n2 && n1 > n3) => n1
      case (n1, n2, n3) if(n2 > n1 && n2 > n3) => n2
      case (n1, n2, n3) if(n3 > n1 && n3 > n2) => n3
    }

  println(s"Largest of ${largestOfThree(10, 11, 12)}")
  println(s"Largest of ${largestOfThree(12, 10, 11)}")
  println(s"Largest of ${largestOfThree(11, 12, 10)}")

  //18.Write a Scala program to check which number is
  // nearest to the value 100 among two given integers. Return 0 if the two numbers are equal.
  def nearestOfHundred(num1: Int, num2: Int): Int =
    (num1, num2) match {
      case (n1, n2) if(n1 == n2) => 0
      case (n1,n2) if(Math.abs(100 - n1) < Math.abs(100 - n2)) => n1
      case (n1,n2) if(Math.abs(100 - n1) > Math.abs(100 - n2)) => n2
    }

  println(s"Nearest to 100: ${nearestOfHundred(90,98)}")
  println(s"Nearest to 100: ${nearestOfHundred(97,93)}")
  println(s"Nearest to 100: ${nearestOfHundred(97,97)}")

  //19.Write a Scala program to check whether two given integers are in the range 40..50 inclusive,
  // or they are both in the range 50..60 inclusive.

  def numCheckTwoRange(num1: Int, num2: Int): Boolean =
    (num1, num2) match {
      case(n1, n2) if(((n1 >= 40 && n1 <= 50) && (n2 >= 40 && n2 <= 50)) || ((n1 >= 50 && n1 <= 60) && (n2 >= 50 && n2 <= 60))) => true
      case _ => false
    }

  println(s"Number Check in Two Ranges: ${numCheckTwoRange(40,45)}")
  println(s"Number Check in Two Ranges: ${numCheckTwoRange(50,55)}")
  println(s"Number Check in Two Ranges: ${numCheckTwoRange(50,40)}")
  println(s"Number Check in Two Ranges: ${numCheckTwoRange(55,50)}")
  println(s"Number Check in Two Ranges: ${numCheckTwoRange(30,70)}")
  println(s"Number Check in Two Ranges: ${numCheckTwoRange(70,40)}")

}
