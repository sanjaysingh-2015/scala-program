object AlgoQuestion extends App {

  //Max Sub-Array Sum problem
  def maxSum(nums : Array[Int]): Int = {
    var maxEndingHere = nums(0)
    var maxSoFar = nums(0)

    for(i <- 1 until nums.length) {
      maxEndingHere = Math.max(nums(i), maxEndingHere + nums(i))
      maxSoFar = Math.max(maxSoFar, maxEndingHere)
    }
    maxSoFar
  }

  val nums = Array(1,-2, 3, -2, 5, -1, 2, -3, 6)
  println(maxSum(nums))

  def longestCommonSubstring(str1: String, str2: String): Int = {
    val m = str1.length
    val n = str2.length
    val dp = Array.ofDim[Int](m+1, n+1)
    var maxLength = 0

    for(i <- 1 to m) {
      for(j <- 1 to n) {
        if(str1(i-1) == str2(j-1)) {
          dp(i)(j) = dp(i-1)(j-1) + 1
          maxLength = Math.max(maxLength, dp(i)(j))
        }
      }
    }
    maxLength
  }

  val str1 = "malayalam"
  val str2 = "kalyanam"
  println(longestCommonSubstring(str1, str2))

  def lengthOfLongestSubstring(s: String): Int = {
    var maxLength = 0
    var start = 0
    val charIndexMap = scala.collection.mutable.Map[Char, Int]()

    for (end <- 0 until s.length) {
      if (charIndexMap.contains(s(end)) && charIndexMap(s(end)) >= start) {
        start = charIndexMap(s(end)) + 1
      }
      charIndexMap(s(end)) = end
      maxLength = Math.max(maxLength, end - start + 1)
    }
    maxLength
  }

  println(lengthOfLongestSubstring("Indonesia"))
}
