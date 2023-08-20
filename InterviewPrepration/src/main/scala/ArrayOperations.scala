import scala.collection.mutable.Stack

object ArrayOperations extends App {
  def findUniqueList(list: List[Int]) : List[Int] = {
    list.foldLeft(List.empty[Int]) {(uniqueList, element) =>
      if(!uniqueList.contains(element)) uniqueList :+ element
      else uniqueList
    }
  }

  def checkString(str : String): Boolean = {
    val stack = scala.collection.mutable.Stack[Char]()
    for(ch <- str) {
      if(ch == '{' || ch == '[' || ch == '(')
        stack.push(ch)
      else if(ch == '}' || ch == ']' || ch == ')') {
        if(stack.isEmpty) {
          return false
        }

        val top = stack.pop()
        if((ch == '}' && top != '{') ||
           (ch == ']' && top != '[') ||
           (ch == ')' && top != '(')) {
          return false
        }
      }
    }
    stack.isEmpty
  }

  def findMissingNumber(numbers: List[Int]): Option[Int] = {
    // Iterate through the list and compare each element with its index + 1
    numbers.zipWithIndex.find { case (value, index) => value != index + 1 }.map(_._2 + 1)
  }

  val myList = List(1,2,2,4,3,5,6,2,1,4,5,8,7,9,6)
  println(findUniqueList(myList))

  val str = "{[]()}"
  var str1 = "{[}()}"
  println(checkString(str))
  println(checkString(str1))

  val list1 = List(1, 2, 3, 5, 5, 6, 7, 8, 9)
  val list2 = List(1, 2, 3, 3, 5, 6, 7, 8, 9)

  val missingNumber1 = findMissingNumber(list1)
  val missingNumber2 = findMissingNumber(list2)

  println(s"Missing number in list1: $missingNumber1")
  println(s"Missing number in list2: $missingNumber2")
}
