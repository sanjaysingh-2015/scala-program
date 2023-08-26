package StringProgramming

//Write a Scala program to read two strings append them together and return the result.
// If the length of the strings is different remove characters
// from the beginning of longer string and make them equal length.
object Problem22 extends App {
  def smartConcat(str1: String, str2: String): String = {
    (str1, str2) match {
      case (s1, s2) if s1.length > s2.length => s1.substring(s1.length - s2.length, s1.length)+s2
      case (s1, s2) if s2.length > s1.length => s1+s2.substring(s2.length - s1.length, s2.length)
      case _ => str1 + str2
    }
  }
  val str1 = "Dipu"
  val str2 = "Singh"
  println(s"Smart Concatenate: $str1 and $str2 = ${smartConcat(str1, str2)}")
}
