object ProblemSolving extends App {
  def twoSum(arr: Array[Int], target: Int): Array[Int] = {
    val numToIndex = scala.collection.mutable.Map[Int, Int]()
    for((num, index) <- arr.zipWithIndex) {
      val complement = target - num
      if (numToIndex.contains(complement)) {
        return Array(numToIndex(complement), index)
      }
      numToIndex(num) = index
    }
    throw new IllegalArgumentException("No solution found")
  }

  val arr = Array(2,8,7,11,10)
  val target = 9
  println(twoSum(arr, target))

  def checkParentheses(str: String): Boolean = {
    val stack = scala.collection.mutable.Stack[Char]()
    for(ch <- str) {
      if(ch == '[' || ch == '{' || ch == '(')
        stack.push(ch)
      else if(ch == ']' || ch == '}' || ch == ')') {
        if (stack.isEmpty) return false

        val top = stack.pop()
        if ((ch == '}' && top != '{') ||
          (ch == ']' && top != '[') ||
          (ch == ')' && top != '('))
          return false
      }
    }
    stack.isEmpty
  }

  println(checkParentheses("{{{}(Sanja)y[()]}}"))

  def mergeInterval(intervals: Array[Array[Int]]): Array[Array[Int]] = {
    if(intervals.isEmpty) intervals

    val sortedIntervals = intervals.sortBy(_.head)
    val mergedIntervals = scala.collection.mutable.ArrayBuffer[Array[Int]]()
    var currentInterval = sortedIntervals(0)
    for(i <- 1 until sortedIntervals.length) {
      val nextInterval = sortedIntervals(i)
      if (currentInterval(1) >= nextInterval(0)) {
        currentInterval(1) = Math.max(currentInterval(1), nextInterval(1))
      } else {
        mergedIntervals += currentInterval
        currentInterval = nextInterval
      }
    }
    mergedIntervals += currentInterval
    mergedIntervals.toArray
  }
  val intervals = Array(Array(2,5), Array(7, 10), Array(3, 8), Array(9, 12))
  print("Merged: ")
  mergeInterval(intervals).map(interval => println(interval.mkString("[", ", ", "]")))
}
