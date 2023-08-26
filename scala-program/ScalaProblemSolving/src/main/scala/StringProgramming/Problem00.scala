package StringProgramming
//Write a Scala program to count occurrence of each word in the String.
object Problem00 extends App {
  def wordRepeatCount(str: String): Map[String, Int] = {
    def wordCountHelper(sList: List[String], sAcc: Map[String, Int]): Map[String, Int] = {
      sList match {
        case Nil => sAcc
        case head :: tail if sAcc.contains(head) =>  wordCountHelper(tail, sAcc + (head -> ((sAcc.get(head).get)+1).toInt))
        case head :: tail => wordCountHelper(tail, sAcc + (head -> 1))
      }
    }
    wordCountHelper(str.split(" ").toList, Map.empty)
  }

  val str = "Hi Ishu how are you doing Good morning, how can I help you"
  println(wordRepeatCount(str).mkString("\n"))
}
