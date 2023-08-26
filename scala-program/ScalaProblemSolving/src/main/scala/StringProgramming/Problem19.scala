package StringProgramming
//Write a Scala program to append two given strings such that,
// if the concatenation creates double characters then omit one of the characters.
object Problem19 extends App {
  def removeDuplicate(str1: String, str2: String): String =
    (str1, str2) match {
      case (s1, s2) if s1.charAt(s1.length-1) == s2.charAt(0) => s1 + s2.substring(1,s2.length)
      case (s1, s2) => s1 + s2
    }

  var str1 = "stood"
  var str2 = "floor"
  println(removeDuplicate(str1, str2))
}
