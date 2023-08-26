package StringProgramming
//Write a Scala program to check if two given strings are rotations of each other.
object Problem18 extends App {
  def areRotational(str1: String, str2: String): Boolean =
    str1+str1 match {
      case str => str.contains(str2)
      case _ => false
    }

  val str1 = "abcde"
  val str2 = "ceab"
  println(s"are $str1 and $str2 rotational ${areRotational(str1, str2)} ")
}
