object First extends App {
  def countWords(str: String): Map[String, Int] = {
    val words = str.split("\\W+")
    words.groupMapReduce(identity)(_ => 1)(_ + _)
  }

  val text = "Hello Scala, how are you, how things are going"
  val wordOccurrences = countWords(text)

  wordOccurrences.foreach { case (word, count) =>
    println(s"$word -> $count")
  }
}
