package StringProgramming
//Write a Scala program to reverse every word in a given string.
object Problem16 extends App {
  def reverseWord(str: String): String = {
    def reverseWordHelper(ws: List[String], wAcc: List[String]): List[String] = {
      def reverseStr(str: String): String = {
        def reverseHelper(s: List[Char], acc: List[Char]): List[Char] = {
          s match {
            case Nil => acc
            case head :: tail => reverseHelper(tail, head :: acc)
          }
        }
        reverseHelper(str.toList, List.empty).mkString
      }
      ws match {
        case Nil => wAcc.reverse
        case wHead :: wTail => reverseWordHelper(wTail, reverseStr(wHead) :: wAcc)
      }
    }
    reverseWordHelper(str.split(" ").toList, List.empty).mkString(" ")
  }

  val str = "This is a test string"
  val result = reverseWord(str)
  println(s"Original: $str")
  println(s"Reveresed: $result")
}