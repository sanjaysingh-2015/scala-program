package StringProgramming
//Write a Scala program to print after removing duplicates from a given string.
object Problem14 extends App {
  def removeDuplicate(str: String): String = {
    def removeHelper(s: List[Char], acc: List[Char]): List[Char] =
      s match {
        case Nil => acc.reverse
        case head :: tail if(acc.contains(head)) => removeHelper(tail, acc)
        case head :: tail => removeHelper(tail, head :: acc)
      }

    removeHelper(str.toList, List.empty).mkString
  }

  val str = "Sanjay Kumar Singh"
  val result = removeDuplicate(str)
  println(s"$str, removed duplicate $result")
}
