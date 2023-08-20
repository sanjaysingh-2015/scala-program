import scala.collection.mutable

object DseExample {
  def main(args:Array[String]): Unit = {
    val input = "sanjaykumarsing"
    val result = lengthOfLongestSubstring(input)
    println(result)
  }

  def lengthOfLongestSubstring(s: String): Int = {
    var maxLength = 0
    var start = 0
    val charMap = mutable.Map[Char, Int]()
    for (end <- 0 until s.length) {
      val currentChar = s.charAt(end)
      // If the current character is already in the map, update the start pointer to skip the repeated character
      if (charMap.contains(currentChar)) {
        start = Math.max(start, charMap(currentChar) + 1)
      }
      // Update the character's index in the map
      charMap(currentChar) = end
      // Update the maximum length
      maxLength = Math.max(maxLength, end - start + 1)
    }
    maxLength
  }
}
