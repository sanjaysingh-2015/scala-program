object StringExamples extends App {

  //Sorting
  def sort(input: List[Char]): List[Char] = {
    val n = input.length
    if (n <= 1) input
    else {
      val (left, right) = input.splitAt(n / 2)
      mergeSort(sort(left), sort(right))
    }
  }
  def mergeSort(left: List[Char], right: List[Char]): List[Char] = {
    (left, right) match {
      case (Nil, rightList) => rightList
      case (leftList, Nil) => leftList
      case (leftHead :: leftTail, rightHead :: rightTail) =>
        if(leftHead < rightHead)
          leftHead :: mergeSort(leftTail, right)
        else
          rightHead :: mergeSort(left, rightTail)
    }
  }

  //Reverse String
  def reverseString(str: String): String = {
    if (str.isEmpty) ""
    else reverseString(str.tail) + str.head
  }

  def countOccuranceOfChar(str: String, ch: Char): Int = {
    str.count(_ == ch)
  }

  def checkPalindrome(str: String): Boolean = {
    str == reverseString(str)
  }

  def countOfVowelAndConsonant(str: String): (Int, Int) = {
    val vowels = "aeiouAEIOU"
    val (vowel, consonant) = str.partition(vowels.contains(_))
    println(vowel+ " - "+consonant)
    (vowel.length, consonant.length)
  }

  def checkAnagram(str: String, str1: String): Boolean = {
    sort(str.toList).mkString == sort(str1.toList).mkString
  }
  val name = "radar"
  println(
    reverseString(name),
    countOccuranceOfChar(name, 'a'),
    checkPalindrome(name),
    countOfVowelAndConsonant(name),
    sort(name.toList).mkString,
    checkAnagram(name, reverseString(name)),
    checkAnagram(name, "sanjay")
  )
}
