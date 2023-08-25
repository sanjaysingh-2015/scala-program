package ds

object ReverseList extends App {
  def reverseList[A](list: List[A]): List[A] = {
    def reverseHelper(original: List[A], reversed: List[A]): List[A] = original match {
      case Nil => reversed
      case head :: tail => reverseHelper(tail, head :: reversed)
    }
    reverseHelper(list, Nil)
  }


  val list = List(1,4,2,7,6,9,8)
  println("Original: "+list)
  println("Reversed: "+reverseList(list))
}
