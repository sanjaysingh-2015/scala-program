package StringProgramming
//Write a Scala program to get the character at the given index within a given String.
// Also print the length of the string.
object Problem01 extends App {
  def charAtIndex(str: String, index: Int): Option[Char] = {
    try {
      println(s"Length of String ${str.length}")

      index match {
        case i if i >= str.length => throw new RuntimeException("Index out of bound")
        case _ => Some(str.charAt(index))
      }
    } catch {
      case e : RuntimeException => {
        println(e.getMessage)
        None
      }
    }
  }

  val index = 15
  val str = "The Great Scala"
  println(s"Charcater at $index in '$str': \"${charAtIndex(str, index)}\"")
}
