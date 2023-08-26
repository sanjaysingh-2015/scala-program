package StringProgramming

// Write a Scala program to read a given string and if the first or last characters are same
// return the string without those characters otherwise return the string unchanged.
object Problem25 extends App {
  def smartCheck(str: String): String =
    str match {
      case s if str.head == str.last => str.tail.init
      case _ => str
    }

  val str = "level"
  println(smartCheck(str))
}
